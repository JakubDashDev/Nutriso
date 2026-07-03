package com.nutriso.api.auth.type;

import java.time.Instant;

public record GeneratedRefreshToken(
  String rawToken,
  Instant expiresAt
) {}
