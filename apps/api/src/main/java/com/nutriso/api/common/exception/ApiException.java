package com.nutriso.api.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}
