package com.nutriso.api.user.dto;

import java.time.LocalDate;

import com.nutriso.api.common.exception.ApiErrorCodes;
import com.nutriso.api.user.enums.ActivityLevel;
import com.nutriso.api.user.enums.GoalType;
import com.nutriso.api.user.enums.Sex;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateProfileRequest(
  @NotNull(message = ApiErrorCodes.REQUIRED)
  LocalDate dateOfBirth,

  @NotNull(message = ApiErrorCodes.REQUIRED)
  Sex sex,

  @NotNull(message = ApiErrorCodes.REQUIRED)
  @Max(value = 350, message = ApiErrorCodes.TOO_BIG)
  @Positive(message = ApiErrorCodes.MUST_BE_POSITIVE)
  Double heightCm,

  @NotNull(message = ApiErrorCodes.REQUIRED)
  ActivityLevel activityLevel,

  @NotNull(message = ApiErrorCodes.REQUIRED)
  GoalType goalType,

  @NotNull(message = ApiErrorCodes.REQUIRED)
  @PositiveOrZero
  @Max(value = 100, message = ApiErrorCodes.TOO_BIG)
  Double weeklyWeightChange
) {}
