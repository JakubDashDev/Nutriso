package com.nutriso.api.user.model;

import com.nutriso.api.common.model.BaseEntity;
import com.nutriso.api.user.enums.ActivityLevel;
import com.nutriso.api.user.enums.GoalType;
import com.nutriso.api.user.enums.TargetMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile_targets")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ProfileTargets extends BaseEntity {
  @Column(name = "activity_level", nullable = false)
  @Enumerated(EnumType.STRING)
  @NonNull
  private ActivityLevel activityLevel;

  @Column(name = "goal_type", nullable = false)
  @Enumerated(EnumType.STRING)
  @NonNull
  private GoalType goalType;

  @Column(name = "weekly_weight_change", nullable = false)
  @NonNull
  private Double weeklyWeightChange;

  @Column(name = "target_mode", nullable = false)
  @Enumerated(EnumType.STRING)
  @NonNull
  private TargetMode targetMode;

  @Column(name = "manual_kcal_target", nullable = true)
  private Integer manualkcalTarget;

  @Column(name = "manual_protein_target", nullable = true)
  private Integer manualproteinTarget;

  @Column(name = "manual_fat_target", nullable = true)
  private Integer manualfatTarget;

  @Column(name = "manual_carbs_target", nullable = true)
  private Integer manualcarbsTarget;

  @JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true)
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  private User user;
}
