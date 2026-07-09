package com.nutriso.api.user.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nutriso.api.common.exception.ApiErrorCodes;
import com.nutriso.api.common.exception.ApiException;
import com.nutriso.api.user.dto.CreateProfileRequest;
import com.nutriso.api.user.dto.ProfileResponse;
import com.nutriso.api.user.dto.UpdateProfileRequest;
import com.nutriso.api.user.mapper.ProfileMapper;
import com.nutriso.api.user.model.Profile;
import com.nutriso.api.user.model.User;
import com.nutriso.api.user.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
  private final ProfileMapper profileMapper;

  public ProfileResponse getUserProfile(User user){
    Profile userProfile = profileRepository.findByUserId(user.getId())
      .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiErrorCodes.NOT_FOUND));

    return ProfileResponse.from(userProfile);
  }

  public ProfileResponse createUserProfile(CreateProfileRequest body, User user) {
    Boolean profileExists = profileRepository.existsByUserId(user.getId());

    if(profileExists)
      throw new ApiException(HttpStatus.CONFLICT, ApiErrorCodes.ALREADY_EXISTS);

    Profile profile = new Profile(
      body.dateOfBirth(),
      body.sex(),
      body.heightCm()
    );  
    profile.setUser(user);

    try {
      Profile savedProfile = profileRepository.save(profile);
      return ProfileResponse.from(savedProfile);
    } catch (DataIntegrityViolationException e) {
      throw new ApiException(HttpStatus.CONFLICT, ApiErrorCodes.ALREADY_EXISTS);
    }
  }

  public ProfileResponse updateProfile(UpdateProfileRequest body, User user) {
    Profile profile = profileRepository.findByUserId(user.getId())
      .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiErrorCodes.NOT_FOUND));

      profileMapper.updateProfileFromRequest(body, profile);

      return ProfileResponse.from(profileRepository.save(profile));
  }
}
