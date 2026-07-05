package com.nutriso.api.auth.dto;

import com.nutriso.api.common.exception.ApiErrorCodes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
  @Email(message = ApiErrorCodes.INVALID_EMAIL)
  @NotBlank(message = ApiErrorCodes.REQUIRED)
  @Size(max = 254, message = ApiErrorCodes.TOO_LONG_MAX)
  String email,

  @NotBlank(message = ApiErrorCodes.REQUIRED)
  @Size(max = 254, message = ApiErrorCodes.TOO_LONG_MAX)
  @Size(min = 3, message = ApiErrorCodes.TOO_SHORT_MIN)
  String name,

  @NotBlank(message = ApiErrorCodes.REQUIRED)
  @Size(max = 72, message = ApiErrorCodes.TOO_LONG_MAX)
  @Size(min = 8, message = ApiErrorCodes.TOO_SHORT_MIN)
  String password,

  @NotBlank(message = ApiErrorCodes.REQUIRED)
  String confirmPassword
) {}
