package com.dankan.domain;

import com.dankan.util.JwtUtil;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @Column(name = "user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "access_token", columnDefinition = "mediumtext")
    private String accessToken;

    @Column(name = "access_token_expired_at", columnDefinition = "date")
    private LocalDateTime accessTokenExpiredAt;

    @Column(name = "refresh_token", columnDefinition = "mediumtext")
    private String refreshToken;

    @Column(name = "refresh_token_expired_at", columnDefinition = "date")
    private LocalDateTime refreshTokenExpiredAt;

    public static Token of(User user) {
        return Token.builder()
                .userId(user.getUserId())
                .accessToken(JwtUtil.createJwt(user))
                .accessTokenExpiredAt(LocalDateTime.now().plusDays(JwtUtil.ACCESS_TOKEN_EXPIRE_TIME))
                .refreshToken(JwtUtil.createRefreshToken())
                .refreshTokenExpiredAt(LocalDateTime.now().plusYears(1L))
                .build();
    }
}
