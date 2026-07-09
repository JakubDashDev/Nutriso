package com.nutriso.api.user.dto;


import com.nutriso.api.common.exception.ApiErrorCodes;
import com.nutriso.api.user.enums.ActivityLevel;
import com.nutriso.api.user.enums.GoalType;
import com.nutriso.api.user.enums.TargetMode;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProfileTargetsRequest(
  @NotNull(message = ApiErrorCodes.REQUIRED)
  ActivityLevel activityLevel,

  @NotNull(message = ApiErrorCodes.REQUIRED)
  GoalType goalType,

  @NotNull(message = ApiErrorCodes.REQUIRED)
  @Max(value = 100, message = ApiErrorCodes.TOO_BIG)
  @Min(value = -100, message = ApiErrorCodes.TOO_BIG)
  Double weeklyWeightChange,

  @NotNull(message = ApiErrorCodes.REQUIRED)
  TargetMode targetMode,

  @PositiveOrZero(message = ApiErrorCodes.MUST_BE_POSITIVE_OR_ZERO)
  Integer manualKcalTarget,

  @PositiveOrZero(message = ApiErrorCodes.MUST_BE_POSITIVE_OR_ZERO)
  Integer manualProteinTarget,

  @PositiveOrZero(message = ApiErrorCodes.MUST_BE_POSITIVE_OR_ZERO)
  Integer manualFatTarget,

  @PositiveOrZero(message = ApiErrorCodes.MUST_BE_POSITIVE_OR_ZERO)
  Integer manualCarbsTarget
) {}
