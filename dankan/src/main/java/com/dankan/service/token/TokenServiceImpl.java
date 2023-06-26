package com.dankan.service.token;

import com.dankan.domain.Token;
import com.dankan.dto.response.login.TokenResponseDto;
import com.dankan.dto.request.token.TokenRequestDto;
import com.dankan.exception.token.TokenNotFoundException;
import com.dankan.exception.user.UserIdNotFoundException;
import com.dankan.repository.TokenRepository;
import com.dankan.repository.UserRepository;
import com.dankan.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

        Token token = tokenRepository.findTokenByAccessTokenAndRefreshToken(accessToken, JwtUtil.getRefreshToken())

                .orElseThrow(() -> new TokenNotFoundException(tokenRequestDto.getUserId().toString()));
        log.info("token1 called");

        token.setAccessToken(
                JwtUtil.createJwt(
                        userRepository.findById(tokenRequestDto.getUserId()).orElseThrow(
                                () -> new UserIdNotFoundException(tokenRequestDto.getUserId().toString()))
                )
        );

        log.info("token2 called");

        token.setAccessTokenExpiredAt(LocalDateTime.now().plusDays(JwtUtil.ACCESS_TOKEN_EXPIRE_TIME));

        tokenRepository.save(token);

        return TokenResponseDto.builder()
                .id(tokenRequestDto.getUserId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiredAt(token.getAccessTokenExpiredAt())
                .refreshTokenExpiredAt(token.getRefreshTokenExpiredAt())
                .build();
    }

    @Override
    public TokenResponseDto findByUserId(final Long id) {
        Token token = tokenRepository.findByUserId(id).orElseThrow(() -> new TokenNotFoundException(id.toString()));

        return TokenResponseDto.of(token);
    }
}
