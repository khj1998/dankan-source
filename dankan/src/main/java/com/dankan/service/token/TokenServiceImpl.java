package com.dankan.service.token;

import com.dankan.domain.Token;
import com.dankan.dto.response.login.TokenResponseDto;
import com.dankan.dto.resquest.token.TokenRequestDto;
import com.dankan.exception.token.TokenNotFoundException;
import com.dankan.exception.user.UserIdNotFoundException;
import com.dankan.repository.TokenRepository;
import com.dankan.repository.UserRepository;
import com.dankan.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public Boolean isExpired() {
        return JwtUtil.isExpired(JwtUtil.getAccessToken());
    }

    @Override
    public TokenResponseDto reissueAccessToken(final TokenRequestDto tokenRequestDto) {
        String accessToken = JwtUtil.getAccessToken();

        Token token = tokenRepository.findTokenByAccessTokenAndRefreshToken(accessToken, tokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new TokenNotFoundException(JwtUtil.getMemberId().toString()));


        token.setAccessToken(
                JwtUtil.createJwt(
                        userRepository.findById(
                                JwtUtil.getMemberId()).orElseThrow(() -> new UserIdNotFoundException(JwtUtil.getMemberId().toString()))

                )
        );

        token.setAccessTokenExpiredAt(LocalDateTime.now().plusDays(JwtUtil.ACCESS_TOKEN_EXPIRE_TIME));

        tokenRepository.save(token);

        return TokenResponseDto.builder()
                .id(JwtUtil.getMemberId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiredAt(token.getAccessTokenExpiredAt())
                .refreshTokenExpiredAt(token.getRefreshTokenExpiredAt())
                .build();
    }
}
