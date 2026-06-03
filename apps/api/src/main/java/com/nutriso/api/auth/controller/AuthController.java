package com.nutriso.api.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutriso.api.auth.dto.AuthResponse;
import com.nutriso.api.auth.dto.LoginRequest;
import com.nutriso.api.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(
        @RequestBody @Valid LoginRequest body
    ) {
        return authService.login(body);
    }
}
