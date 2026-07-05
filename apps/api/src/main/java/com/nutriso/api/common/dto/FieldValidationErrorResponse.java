package com.nutriso.api.common.dto;

import java.util.Map;

public record FieldValidationErrorResponse(
  int status,
  String message,
  Map<String, String> errors
) {}
