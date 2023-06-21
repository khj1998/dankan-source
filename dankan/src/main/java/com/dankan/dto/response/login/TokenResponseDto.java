package com.dankan.dto.response.login;

import com.dankan.domain.Token;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private UUID id;
    private LocalDateTime accessTokenExpiredAt;
    private LocalDateTime refreshTokenExpiredAt;

    public static TokenResponseDto of(Token token) {
        return TokenResponseDto.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .id(token.getUserId())
                .accessTokenExpiredAt(token.getAccessTokenExpiredAt())
                .refreshTokenExpiredAt(token.getRefreshTokenExpiredAt())
                .build();
    }
}
