package com.nutriso.api.auth.type;

public record ApiError(
  int status,
  String message
) {}
