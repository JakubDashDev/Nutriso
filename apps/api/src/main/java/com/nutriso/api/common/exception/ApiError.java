package com.nutriso.api.common.exception;

import java.util.Map;

public record ApiError(
    int status,
    String message,
    String path,
    Map<String, String> errors
) {}
