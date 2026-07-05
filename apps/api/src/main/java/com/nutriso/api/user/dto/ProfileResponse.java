package com.nutriso.api.user.dto;

import java.time.LocalDate;

import com.nutriso.api.user.enums.ActivityLevel;
import com.nutriso.api.user.enums.GoalType;
import com.nutriso.api.user.enums.Sex;
import com.nutriso.api.user.enums.TargetMode;
import com.nutriso.api.user.model.Profile;

public record ProfileResponse(
  LocalDate dateOfBirth,
  Sex sex,
  Double heightCm,
  ActivityLevel activityLevel,
  GoalType goalType,
  Double weeklyWeightChange,
  TargetMode targetMode
) {
  public static ProfileResponse from(Profile profile) {
    return new ProfileResponse(
      profile.getDateOfBirth(),
      profile.getSex(),
      profile.getHeightCm(),
      profile.getActivityLevel(),
      profile.getGoalType(),
      profile.getWeeklyWeightChange(),
      profile.getTargetMode()
    );
  }
}