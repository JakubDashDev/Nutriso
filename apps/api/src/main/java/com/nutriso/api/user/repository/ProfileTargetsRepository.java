package com.nutriso.api.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutriso.api.user.model.ProfileTargets;

public interface ProfileTargetsRepository extends JpaRepository<ProfileTargets, UUID> {
  Optional<ProfileTargets> findByUserId(UUID userId);
}
