package com.dankan.service.user;

import com.dankan.domain.Authority;
import com.dankan.domain.Token;
import com.dankan.domain.User;
import com.dankan.dto.response.login.LoginResponseDto;
import com.dankan.dto.response.login.OauthLoginResponseDto;
import com.dankan.dto.response.logout.LogoutResponseDto;
import com.dankan.dto.response.user.UserResponseDto;
import com.dankan.exception.token.TokenNotFoundException;
import com.dankan.exception.user.UserNameExistException;
import com.dankan.exception.user.UserIdNotFoundException;
import com.dankan.exception.user.UserNameNotFoundException;
import com.dankan.repository.TokenRepository;
import com.dankan.repository.UserRepository;
import com.dankan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository,TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public boolean checkDuplicatedName(String name) {
        final Optional<UserResponseDto> user = userRepository.findByNickname(name);

        if(user.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public UserResponseDto modifyNickName(final String name) {
        boolean isDuplicated = checkDuplicatedName(name);

        log.info("duplicated: {}", isDuplicated);

        if(isDuplicated == true) {
            throw new UserNameExistException(name);
        }
        else
        {
            User user = userRepository.findById(JwtUtil.getMemberId()).orElseThrow(() -> new UserIdNotFoundException(JwtUtil.getMemberId().toString()));

            user.setNickname(name);

            userRepository.save(user);

            return UserResponseDto.of(user);
        }
    }

    @Override
    public UserResponseDto modifyProfileImg(final String imgUrl) {
        User user = userRepository.findById(JwtUtil.getMemberId()).orElseThrow(() -> new UserIdNotFoundException(JwtUtil.getMemberId().toString()));

        user.setProfileImg(imgUrl);

        userRepository.save(user);

        return UserResponseDto.of(user);
    }

    @Override
    public Optional<User> checkDuplicatedEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public LoginResponseDto signUp(OauthLoginResponseDto oauthLoginResponseDto) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .authorities(Arrays.asList(authority))
                .email(oauthLoginResponseDto.getEmail())
                .nickname(oauthLoginResponseDto.getNickname())
                .profileImg(oauthLoginResponseDto.getProfileImg())
                .userType(0L) // 카카오 로그인, 그외 로그인 타입을 객체 지향적으로 분리해 of를 쓸 예정입니다. 더 고민해봐야해요.
                .build();
        userRepository.save(user);

        Token token = Token.of(user);
        tokenRepository.save(token);

        return LoginResponseDto.of(user,token);
    }

    @Override
    public LoginResponseDto signIn(User user) {
        Token token = tokenRepository.findByUserId(user.getUserId()).orElseThrow(() -> new UserIdNotFoundException(user.getUserId().toString()));

        return LoginResponseDto.of(user,token);
    }

    @Override
    public UserResponseDto findUserByNickname() {
        return userRepository.findByUserId(JwtUtil.getMemberId()).orElseThrow(
                () -> new UserIdNotFoundException(JwtUtil.getMemberId().toString())
        );
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findUserList();
    }

    @Override
    public UserResponseDto findUserByNickname(String name) {
        return userRepository.findByNickname(name).orElseThrow(
                () -> new UserNameNotFoundException(name)
        );
    }

    @Override
    public void deleteUser() {
        userRepository.deleteById(JwtUtil.getMemberId());

    }

    @Override
    public void deleteUser(final String name) {
        userRepository.delete(userRepository.findUserByNickname(name).orElseThrow(() -> new UserNameExistException(name)));
    }

    @Override
    public LogoutResponseDto logout() {
        String expiredAccessToken = JwtUtil.logout();

        Token token = tokenRepository.findByUserId(JwtUtil.getMemberId()).orElseThrow(() -> new TokenNotFoundException(JwtUtil.getMemberId().toString()));

        token.setAccessToken(expiredAccessToken);
        token.setAccessTokenExpiredAt(LocalDateTime.now().minusYears(1L));

        tokenRepository.save(token);

        return new LogoutResponseDto(expiredAccessToken);
    }

    @Override
    public List<Authority> getAuthorities() {
        return userRepository.findUserByUserId(JwtUtil.getMemberId()).orElseThrow(
                () -> new UserIdNotFoundException(JwtUtil.getMemberId().toString())
        ).getAuthorities();
    }
}
