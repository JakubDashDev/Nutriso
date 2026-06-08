package com.nutriso.api.auth.controller;


import java.time.Duration;
import java.time.Instant;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutriso.api.auth.dto.LoginRequest;
import com.nutriso.api.auth.dto.LoginResponse;
import com.nutriso.api.auth.service.AuthService;
import com.nutriso.api.auth.type.AuthResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @RequestBody @Valid LoginRequest body,
        @RequestHeader(value = "User-Agent", defaultValue = "unknown") String userAgent
    ) {
        AuthResponse authResponse = authService.login(body, userAgent);

        ResponseCookie accessCookie = ResponseCookie.from("access_token", authResponse.accessToken())
            .httpOnly(true)
            .secure(true)
            .sameSite("LAX")
            .path("/")
            .maxAge(Duration.between(Instant.now(), authResponse.accessTokenExpiresAt().toInstant()))
            .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", authResponse.refreshToken())
            .httpOnly(true)
            .secure(true)
            .sameSite("LAX")
            .path("/api/v1/auth")
            .maxAge(Duration.between(Instant.now(), authResponse.refreshTokenExpiresAt()))
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(authResponse.loginResponse());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @CookieValue(value = "refresh_token", required = false) String refreshToken
    ) {

        if(refreshToken != null) {
            authService.logout(refreshToken);
        }

        ResponseCookie accessCookie = ResponseCookie.from("access_token", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("LAX")
            .path("/")
            .maxAge(Duration.ZERO)
            .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("LAX")
            .path("/api/v1/auth")
            .maxAge(Duration.ZERO)
            .build();

        return ResponseEntity.noContent()
            .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .build();
    }
}
