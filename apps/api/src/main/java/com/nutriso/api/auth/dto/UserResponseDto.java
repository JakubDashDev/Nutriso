package com.nutriso.api.auth.dto;

import java.util.UUID;

import com.nutriso.api.user.enums.Role;


public record UserResponseDto(
    UUID id,
    String email,
    Role role
) {}
