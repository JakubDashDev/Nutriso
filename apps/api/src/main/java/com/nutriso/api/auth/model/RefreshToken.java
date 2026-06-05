package com.nutriso.api.auth.model;

import java.time.Instant;

import com.nutriso.api.common.model.BaseEntity;
import com.nutriso.api.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class RefreshToken extends BaseEntity {
    
    @Column(name = "token_hash", nullable = false, unique = true)
    @NonNull
    @NotBlank
    private String tokenHash;

    @Column(name = "expires_at", nullable = false)
    @NonNull
    private Instant expiresAt;

    @Column(name = "revoked_at", nullable = true)
    private Instant revokedAt;

    @Column(name = "user_agent", nullable = false)
    @NonNull
    private String userAgent;

    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}
