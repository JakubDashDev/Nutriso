package com.nutriso.api.common.exception;

public record ApiError(
    int status,
    String message,
    String path
) {}
