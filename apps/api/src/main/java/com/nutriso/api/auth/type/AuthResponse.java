package com.nutriso.api.auth.type;

import java.time.Instant;
import java.util.Date;

import com.nutriso.api.auth.dto.LoginResponse;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    Date accessTokenExpiresAt,
    Instant refreshTokenExpiresAt,
    LoginResponse loginResponse
) {}
