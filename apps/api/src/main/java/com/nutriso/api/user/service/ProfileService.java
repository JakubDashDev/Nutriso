package com.nutriso.api.user.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nutriso.api.common.exception.ApiErrorCodes;
import com.nutriso.api.common.exception.ApiException;
import com.nutriso.api.user.dto.CreateProfileRequest;
import com.nutriso.api.user.dto.ProfileResponse;
import com.nutriso.api.user.dto.UpdateProfileTargetsRequest;
import com.nutriso.api.user.enums.TargetMode;
import com.nutriso.api.user.model.Profile;
import com.nutriso.api.user.model.User;
import com.nutriso.api.user.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;

  public ProfileResponse createUserProfile(CreateProfileRequest body, User user) {
    Boolean profileExists = profileRepository.existsByUserId(user.getId());

    if(profileExists)
      throw new ApiException(HttpStatus.CONFLICT, ApiErrorCodes.ALREADY_EXISTS);

    Profile profile = new Profile(
      body.dateOfBirth(),
      body.sex(),
      body.heightCm(),
      body.activityLevel(),
      body.goalType(),
      body.weeklyWeightChange()
    );  
    profile.setUser(user);

    
    try {
      Profile savedProfile = profileRepository.save(profile);
      return ProfileResponse.from(savedProfile);
    } catch (DataIntegrityViolationException e) {
      throw new ApiException(HttpStatus.CONFLICT, ApiErrorCodes.ALREADY_EXISTS);
    }
  }

  public ProfileResponse updateProfileTargets(UpdateProfileTargetsRequest body, User user) {
    Profile profile = profileRepository.findByUserId(user.getId())
      .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiErrorCodes.NOT_FOUND));

    if(body.targetMode() == TargetMode.AUTO) {
      profile.setTargetMode(TargetMode.AUTO);
      return ProfileResponse.from(profileRepository.save(profile));
    }

    if(body.manualProteinTarget() == null || body.manualFatTarget() == null || body.manualCarbsTarget() == null || body.manualKcalTarget() == null)
      throw new ApiException(HttpStatus.BAD_REQUEST, ApiErrorCodes.INVALID_REQUEST_BODY);

    if(!isCaloriesCorrect(body)) 
      throw new ApiException(HttpStatus.BAD_REQUEST, ApiErrorCodes.INVALID_MACROS_TOTAL);

    profile.setManualkcalTarget(body.manualKcalTarget());
    profile.setManualproteinTarget(body.manualProteinTarget());
    profile.setManualfatTarget(body.manualFatTarget());
    profile.setManualcarbsTarget(body.manualCarbsTarget());
    profile.setTargetMode(TargetMode.MANUAL);

    return ProfileResponse.from(profileRepository.save(profile));
  }

  private boolean isCaloriesCorrect(UpdateProfileTargetsRequest body) {
    int macroCalories = 
      body.manualProteinTarget() * 4 +
      body.manualFatTarget() * 9 +
      body.manualCarbsTarget() * 4;

    return macroCalories == body.manualKcalTarget();
  }
}
