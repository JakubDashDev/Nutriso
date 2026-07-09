package com.nutriso.api.user.dto;

import java.time.LocalDate;

import com.nutriso.api.common.exception.ApiErrorCodes;
import com.nutriso.api.user.enums.Sex;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

public record UpdateProfileRequest(
  LocalDate dateOfBirth,
  
  Sex sex,

  @Positive(message = ApiErrorCodes.MUST_BE_POSITIVE)
  @Max(value = 350, message = ApiErrorCodes.TOO_BIG)
  Double heightCm
) {}
