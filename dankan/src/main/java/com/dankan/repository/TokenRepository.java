package com.dankan.repository;

import com.dankan.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    public Optional<Token> findByUserId(UUID userId);

    public Optional<Token> findTokenByAccessTokenAndRefreshToken(String accessToken, String refreshToken);
}
