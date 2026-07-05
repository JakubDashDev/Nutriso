package com.nutriso.api.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutriso.api.user.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
  Boolean existsByUserId(UUID userId);

  Optional<Profile> findByUserId(UUID userId);
}
