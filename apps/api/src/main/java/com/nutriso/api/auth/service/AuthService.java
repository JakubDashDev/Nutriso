package com.nutriso.api.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nutriso.api.auth.dto.LoginRequest;
import com.nutriso.api.auth.dto.LoginResponse;
import com.nutriso.api.auth.type.AuthResponse;
import com.nutriso.api.auth.type.GeneratedAccessToken;
import com.nutriso.api.auth.type.GeneratedRefreshToken;
import com.nutriso.api.user.model.User;
import com.nutriso.api.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request, String userAgent) {
        User user = userService.findByEmail(request.email())
            .orElseThrow(() -> invalidCredentials());

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            invalidCredentials();
        }

        return createAuthResponse(user, userAgent);
    }

    private AuthResponse createAuthResponse(User user, String userAgent) {
        GeneratedAccessToken accessToken = jwtService.generateAccessToken(user);
        GeneratedRefreshToken refreshToken = refreshTokenService.generateRefreshTokenAndSave(user, userAgent);

        LoginResponse loginResponse = new LoginResponse(user.getId(), user.getEmail(), user.getRole());

        return new AuthResponse(accessToken.token(), refreshToken.rawToken(), accessToken.expiresAt(), refreshToken.expiresAt(), loginResponse);
    }

    private ResponseStatusException invalidCredentials() {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}
