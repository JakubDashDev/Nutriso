package com.nutriso.api.user.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nutriso.api.common.exception.ApiErrorCodes;
import com.nutriso.api.common.exception.ApiException;
import com.nutriso.api.user.dto.ProfileTargetsRequest;
import com.nutriso.api.user.dto.ProfileTargetsResponse;
import com.nutriso.api.user.enums.TargetMode;
import com.nutriso.api.user.model.ProfileTargets;
import com.nutriso.api.user.model.User;
import com.nutriso.api.user.repository.ProfileTargetsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileTargetsService {
  private final ProfileTargetsRepository profileTargetsRepository;

  public ProfileTargetsResponse createProfileTargets(ProfileTargetsRequest body, User user) {
    Optional<ProfileTargets> profile = profileTargetsRepository.findByUserId(user.getId());

    if(profile.isPresent()) 
      throw new ApiException(HttpStatus.CONFLICT, ApiErrorCodes.ALREADY_EXISTS);

    if(!isGoalTypeCorrect(body))
      throw new ApiException(HttpStatus.BAD_REQUEST, ApiErrorCodes.INVALID_GOAL_TYPE);

    ProfileTargets newProfileTargets = new ProfileTargets(
      body.activityLevel(),
      body.goalType(),
      body.weeklyWeightChange(),
      body.targetMode()
    );
    newProfileTargets.setUser(user);    

    if(body.targetMode() == TargetMode.AUTO) 
      return ProfileTargetsResponse.from(profileTargetsRepository.save(newProfileTargets));
    
    if(body.manualProteinTarget() == null || body.manualFatTarget() == null || body.manualCarbsTarget() == null || body.manualKcalTarget() == null)
      throw new ApiException(HttpStatus.BAD_REQUEST, ApiErrorCodes.INVALID_REQUEST_BODY);

    if(!isCaloriesCorrect(body)) 
    throw new ApiException(HttpStatus.BAD_REQUEST, ApiErrorCodes.INVALID_MACROS_TOTAL);

    newProfileTargets.setManualkcalTarget(body.manualKcalTarget());
    newProfileTargets.setManualproteinTarget(body.manualProteinTarget());
    newProfileTargets.setManualfatTarget(body.manualFatTarget());
    newProfileTargets.setManualcarbsTarget(body.manualCarbsTarget());
    newProfileTargets.setTargetMode(TargetMode.MANUAL);

    return ProfileTargetsResponse.from(profileTargetsRepository.save(newProfileTargets));
  }

  public ProfileTargetsResponse updateProfileTargets(ProfileTargetsRequest body, User user){
    ProfileTargets profileTargets = profileTargetsRepository.findByUserId(user.getId())
      .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiErrorCodes.NOT_FOUND));

    if(!isGoalTypeCorrect(body))
      throw new ApiException(HttpStatus.BAD_REQUEST, ApiErrorCodes.INVALID_GOAL_TYPE);

    if(body.targetMode() == TargetMode.AUTO) {
      profileTargets.setActivityLevel(body.activityLevel());
      profileTargets.setGoalType(body.goalType());
      profileTargets.setWeeklyWeightChange(body.weeklyWeightChange());
      profileTargets.setTargetMode(TargetMode.AUTO);

      return ProfileTargetsResponse.from(profileTargetsRepository.save(profileTargets));
    }

    if(body.manualProteinTarget() == null || body.manualFatTarget() == null || body.manualCarbsTarget() == null || body.manualKcalTarget() == null)
      throw new ApiException(HttpStatus.BAD_REQUEST, ApiErrorCodes.INVALID_REQUEST_BODY);

    if(!isCaloriesCorrect(body)) 
      throw new ApiException(HttpStatus.BAD_REQUEST, ApiErrorCodes.INVALID_MACROS_TOTAL);

    profileTargets.setActivityLevel(body.activityLevel());
    profileTargets.setGoalType(body.goalType());
    profileTargets.setWeeklyWeightChange(body.weeklyWeightChange());
    profileTargets.setTargetMode(TargetMode.MANUAL);
    profileTargets.setManualkcalTarget(body.manualKcalTarget());
    profileTargets.setManualproteinTarget(body.manualProteinTarget());
    profileTargets.setManualfatTarget(body.manualFatTarget());
    profileTargets.setManualcarbsTarget(body.manualCarbsTarget());
    profileTargets.setTargetMode(TargetMode.MANUAL);

    return ProfileTargetsResponse.from(profileTargetsRepository.save(profileTargets));
  }

  private boolean isCaloriesCorrect(ProfileTargetsRequest body) {
    int macroCalories = 
      body.manualProteinTarget() * 4 +
      body.manualFatTarget() * 9 +
      body.manualCarbsTarget() * 4;

    return macroCalories == body.manualKcalTarget();
  }

  private boolean isGoalTypeCorrect(ProfileTargetsRequest body) {
    return switch (body.goalType()) {
      case MAINTAIN -> body.weeklyWeightChange() == 0;
      case GAIN -> body.weeklyWeightChange() > 0;
      case LOSE -> body.weeklyWeightChange() < 0;
    };
  }
}
