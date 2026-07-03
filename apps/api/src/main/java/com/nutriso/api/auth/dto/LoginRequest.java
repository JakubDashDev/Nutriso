package com.nutriso.api.auth.dto;

import com.nutriso.api.common.exception.ApiErrorCodes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
  @Email(message = ApiErrorCodes.INVALID_EMAIL)
  @NotBlank(message = ApiErrorCodes.REQUIRED)
  String email,

  @NotBlank(message = ApiErrorCodes.REQUIRED)
  String password
) {}