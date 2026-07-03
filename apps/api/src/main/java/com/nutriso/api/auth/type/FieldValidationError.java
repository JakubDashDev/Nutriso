package com.nutriso.api.auth.type;

import java.util.Map;

public record FieldValidationError(
  int status,
  String message,
  Map<String, String> errors
) {}
