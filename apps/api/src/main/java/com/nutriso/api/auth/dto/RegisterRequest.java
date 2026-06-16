package com.nutriso.api.auth.dto;

import com.nutriso.api.common.exception.ApiValidationError;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @Email(message = ApiValidationError.INVALID_EMAIL)
    @NotBlank(message = ApiValidationError.REQUIRED)
    String email,

    @NotBlank(message = ApiValidationError.REQUIRED)
    @Size(max = 99, message = ApiValidationError.TOO_LONG_MAX)
    @Size(min = 3, message = ApiValidationError.TOO_SHORT_MIN)
    String name,

    @NotBlank(message = ApiValidationError.REQUIRED)
    @Size(max = 99, message = ApiValidationError.TOO_LONG_MAX)
    @Size(min = 8, message = ApiValidationError.TOO_SHORT_MIN)
    String password,

    @NotBlank(message = ApiValidationError.REQUIRED)
    String confirmPassword
) {}
