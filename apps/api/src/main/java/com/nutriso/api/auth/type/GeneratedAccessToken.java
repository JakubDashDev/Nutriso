package com.nutriso.api.auth.type;

import java.util.Date;

public record GeneratedAccessToken(
    String token,
    Date expiresAt
) {}
