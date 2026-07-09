package com.nutriso.api.user.dto;

import com.nutriso.api.user.enums.ActivityLevel;
import com.nutriso.api.user.enums.GoalType;
import com.nutriso.api.user.enums.TargetMode;
import com.nutriso.api.user.model.ProfileTargets;


public record ProfileTargetsResponse(
  ActivityLevel activityLevel,

  GoalType goalType,

  Double weeklyWeightChange,

  TargetMode targetMode,

  Integer manualKcalTarget,

  Integer manualProteinTarget,

  Integer manualFatTarget,

  Integer manualCarbsTarget
) {
  public static ProfileTargetsResponse from(ProfileTargets profileTargets) {
    return new ProfileTargetsResponse(
      profileTargets.getActivityLevel(),
      profileTargets.getGoalType(),
      profileTargets.getWeeklyWeightChange(),
      profileTargets.getTargetMode(),
      profileTargets.getManualkcalTarget(),
      profileTargets.getManualproteinTarget(),
      profileTargets.getManualfatTarget(),
      profileTargets.getManualcarbsTarget()
    );
  }
}
