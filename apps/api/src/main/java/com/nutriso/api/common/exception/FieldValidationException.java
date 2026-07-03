package com.nutriso.api.common.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FieldValidationException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
    private final Map<String, String> errors;
}
