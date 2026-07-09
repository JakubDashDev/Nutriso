package com.nutriso.api.user.dto;

import java.time.LocalDate;

import com.nutriso.api.user.enums.Sex;
import com.nutriso.api.user.model.Profile;

public record ProfileResponse(
  LocalDate dateOfBirth,
  Sex sex,
  Double heightCm
) {
  public static ProfileResponse from(Profile profile) {
    return new ProfileResponse(
      profile.getDateOfBirth(),
      profile.getSex(),
      profile.getHeightCm()
    );
  }
}