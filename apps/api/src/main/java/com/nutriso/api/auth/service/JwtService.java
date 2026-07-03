package com.nutriso.api.auth.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nutriso.api.auth.type.GeneratedAccessToken;
import com.nutriso.api.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access-token-expiration-ms}")
  private long accessTokenExpirationMs;
    
  public GeneratedAccessToken generateAccessToken(User user) {
    Date now = new Date();
    Date expiresAt = new Date(now.getTime() + accessTokenExpirationMs);

    String token =  Jwts.builder()
      .subject(user.getId().toString())
      .claim("email", user.getEmail())
      .claim("role", user.getRole().name())
      .issuedAt(now)
      .expiration(expiresAt)
      .signWith(getSigningKey())
      .compact();

    return new GeneratedAccessToken(token, expiresAt);
  }

  public boolean isTokenValid(String token, User user) {
    try {
      UUID userId = extractUserId(token);
      return userId.equals(user.getId()) && !isTokenExpired(token);
    } catch (Exception e) {
      return false;
    }
  }

  public UUID extractUserId(String token) {
    return UUID.fromString(extractClaim(token, Claims::getSubject));
  }

  private boolean isTokenExpired(String token) {
    Date expiration = extractClaim(token, Claims::getExpiration);
    return expiration.before(new Date());
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = Jwts
      .parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();

    return claimsResolver.apply(claims);
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
