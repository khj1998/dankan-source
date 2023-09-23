package com.dankan.repository;

import com.dankan.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    public Optional<Token> findByUserId(Long userId);

    public Optional<Token> findTokenByAccessTokenAndRefreshToken(String accessToken, String refreshToken);
    public Optional<Token> findTokenByAccessToken(String accessToken);
}
