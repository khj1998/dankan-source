package com.dankan.dto.response.login;

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
}
