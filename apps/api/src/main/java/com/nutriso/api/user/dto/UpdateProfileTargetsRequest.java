package com.nutriso.api.user.dto;

import com.nutriso.api.common.exception.ApiErrorCodes;
import com.nutriso.api.user.enums.TargetMode;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProfileTargetsRequest(
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
