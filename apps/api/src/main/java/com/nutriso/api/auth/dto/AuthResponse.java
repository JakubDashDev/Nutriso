package com.nutriso.api.auth.dto;


public record AuthResponse(
    String token,
    UserResponseDto user
) {
}
