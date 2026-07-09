package com.nutriso.api.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutriso.api.user.dto.CreateProfileRequest;
import com.nutriso.api.user.dto.ProfileResponse;
import com.nutriso.api.user.dto.ProfileTargetsRequest;
import com.nutriso.api.user.dto.ProfileTargetsResponse;
import com.nutriso.api.user.dto.UpdateProfileRequest;
import com.nutriso.api.user.model.User;
import com.nutriso.api.user.service.ProfileService;
import com.nutriso.api.user.service.ProfileTargetsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/profile")
@RequiredArgsConstructor
public class ProfileController {
  private final ProfileService profileService;
  private final ProfileTargetsService profileTargetsService;

  @GetMapping
  public ProfileResponse getProfile(@AuthenticationPrincipal User user) {
    return profileService.getUserProfile(user);
  }

  @PostMapping
  public ProfileResponse createProfile(
    @RequestBody @Valid CreateProfileRequest body,
    @AuthenticationPrincipal User user
  ) {
    return profileService.createUserProfile(body, user);
  }

  @PatchMapping
  public ProfileResponse updateProfile(
    @RequestBody @Valid UpdateProfileRequest body,
    @AuthenticationPrincipal User user
  ) {
    return profileService.updateProfile(body, user);
  }

  @PostMapping("/targets")
  public ProfileTargetsResponse createProfileTargets(
    @RequestBody @Valid ProfileTargetsRequest body,
    @AuthenticationPrincipal User user
  ) {
    return profileTargetsService.createProfileTargets(body, user);
  }

  @PutMapping("/targets")
  public ProfileTargetsResponse updateProfileTargets(
    @RequestBody @Valid ProfileTargetsRequest body,
    @AuthenticationPrincipal User user
  ) {
    return profileTargetsService.updateProfileTargets(body, user);
  }
}
