package com.nutriso.api.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nutriso.api.auth.model.RefreshToken;
import com.nutriso.api.auth.repository.RefreshTokenRepository;
import com.nutriso.api.auth.type.GeneratedRefreshToken;
import com.nutriso.api.user.model.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    
    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpiresMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public GeneratedRefreshToken generateRefreshTokenAndSave(User user, String userAgent) {
        String rawToken = generateRawToken();
        String tokenHash = hashToken(rawToken);

        Instant expiresAt = Instant.now().plus(Duration.ofMillis(refreshTokenExpiresMs));

        refreshTokenRepository.save(new RefreshToken(
            tokenHash,
            expiresAt,
            null,
            normalizeUserAgent(userAgent),
            user
        ));

        return new GeneratedRefreshToken(rawToken, expiresAt);
    }

    @Transactional
    public void logout(String rawToken) {
        String tokenHash = hashToken(rawToken);

        refreshTokenRepository.findByTokenHash(tokenHash)
            .filter(refreshToken -> refreshToken.getRevokedAt() == null)
            .ifPresent(refreshToken -> refreshToken.setRevokedAt(Instant.now()));
    }

    private String generateRawToken() {
        byte[] bytes = new byte[64];
        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashToken(String rawToken) {
        byte[] hash = sha256Digest().digest(rawToken.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }

    private String normalizeUserAgent(String userAgent) {
        return userAgent == null || userAgent.isBlank() ? "unknown" : userAgent;
    }

    private MessageDigest sha256Digest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }
}
