package com.nutriso.api.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nutriso.api.auth.dto.AuthResponse;
import com.nutriso.api.auth.dto.LoginRequest;
import com.nutriso.api.auth.dto.UserResponseDto;
import com.nutriso.api.user.model.User;
import com.nutriso.api.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        User user = userService.findByEmail(request.email())
            .orElseThrow(() -> invalidCredentials());

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw invalidCredentials();
        }

        return createAuthResponse(user);
    }

    private AuthResponse createAuthResponse(User user) {
        return new AuthResponse(
            jwtService.generateAccessToken(user),
            new UserResponseDto(user.getId(), user.getEmail(), user.getRole())
        );
    }

    private ResponseStatusException invalidCredentials() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}
