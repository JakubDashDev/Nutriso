package com.nutriso.api.auth.dto;

import com.nutriso.api.common.exception.ApiErrorCodes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
  @Email(message = ApiErrorCodes.INVALID_EMAIL)
  @NotBlank(message = ApiErrorCodes.REQUIRED)
  @Size(max = 254, message = ApiErrorCodes.TOO_LONG_MAX)
  String email,

  @NotBlank(message = ApiErrorCodes.REQUIRED)
  @Size(max = 72, message = ApiErrorCodes.TOO_LONG_MAX)
  String password
) {}