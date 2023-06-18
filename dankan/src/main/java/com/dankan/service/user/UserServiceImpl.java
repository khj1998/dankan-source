package com.dankan.service.user;

import com.dankan.domain.Authority;
import com.dankan.domain.Token;
import com.dankan.domain.User;
import com.dankan.dto.response.login.LoginResponseDto;
import com.dankan.dto.response.login.OauthLoginResponseDto;
import com.dankan.exception.user.UserNotFoundException;
import com.dankan.repository.TokenRepository;
import com.dankan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

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
        final Optional<User> user = userRepository.findByNickname(name);

        user.orElseThrow(() -> new UserNotFoundException(name));

        return true;
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
                .profileImg(oauthLoginResponseDto.getProfilImg())
                .userType(0) // 카카오 로그인, 그외 로그인 타입을 객체 지향적으로 분리해 of를 쓸 예정입니다. 더 고민해봐야해요.
                .build();
        userRepository.save(user);

        Token token = Token.of(user);
        tokenRepository.save(token);

        return LoginResponseDto.of(user,token);
    }

    @Override
    public LoginResponseDto signIn(User user) {
        Token token = tokenRepository.findByUserId(user.getUserId());
        return LoginResponseDto.of(user,token);
    }
}
