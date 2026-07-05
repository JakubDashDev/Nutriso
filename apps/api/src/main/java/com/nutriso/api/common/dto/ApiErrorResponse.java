package com.nutriso.api.common.dto;

public record ApiErrorResponse(
  int status,
  String message
) {}
