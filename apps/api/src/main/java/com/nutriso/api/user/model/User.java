package com.nutriso.api.user.model;

import com.nutriso.api.common.model.BaseEntity;
import com.nutriso.api.user.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    @NonNull
    private String email;

    @Column(name = "password_hash", nullable = false)
    @NonNull
    private String passwordHash;
    
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private Role role;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}
