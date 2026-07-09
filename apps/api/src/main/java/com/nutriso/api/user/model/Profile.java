package com.nutriso.api.user.model;

import java.time.LocalDate;

import com.nutriso.api.common.model.BaseEntity;
import com.nutriso.api.user.enums.Sex;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
public class Profile extends BaseEntity {
  @Column(name = "date_of_birth", nullable = false)
  @NonNull
  private LocalDate dateOfBirth;

  @Column(name = "sex", nullable = false)
  @Enumerated(EnumType.STRING)
  @NonNull
  private Sex sex;

  @Column(name = "height_cm", nullable = false)
  @NonNull
  private Double heightCm;

  @JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true)
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  private User user;
}
