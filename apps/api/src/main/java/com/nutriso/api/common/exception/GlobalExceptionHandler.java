package com.nutriso.api.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.nutriso.api.auth.type.ApiError;
import com.nutriso.api.auth.type.FieldValidationError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(
        ResponseStatusException exception,
        HttpServletRequest request
    ) {
        return ResponseEntity
            .status(exception.getStatusCode())
            .body(new ApiError(
                exception.getStatusCode().value(),
                messageFor(exception)
            ));
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<FieldValidationError> handleApiFieldException(
        FieldValidationException exception,
        HttpServletRequest request
    ) {
        return ResponseEntity
            .status(exception.getStatus())
            .body(new FieldValidationError(
                exception.getStatus().value(),
                exception.getMessage(),
                exception.getErrors()
            ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<FieldValidationError> handleBadRequestException(
        HttpMessageNotReadableException exception,
        HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        MismatchedInputException mismatchedInputException =
            findCause(exception, MismatchedInputException.class);

        if (mismatchedInputException != null) {
            String field = fieldPath(mismatchedInputException);

            if (field != null && !field.isBlank()) {
                errors.put(field, ApiErrorCodes.INVALID_TYPE);
            }
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new FieldValidationError(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request body",
                errors
            ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldValidationError> handleValidationException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new FieldValidationError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
            ));
    }

    private String messageFor(ResponseStatusException exception) {
        return exception.getReason() == null ? "Request failed" : exception.getReason();
    }

    private String fieldPath(JsonMappingException exception) {
        return exception.getPath()
            .stream()
            .map(reference -> reference.getFieldName() != null
                ? reference.getFieldName()
                : "[" + reference.getIndex() + "]")
            .collect(Collectors.joining("."));
    }

    private <T extends Throwable> T findCause(Throwable throwable, Class<T> type) {
        while (throwable != null) {
            if (type.isInstance(throwable)) {
                return type.cast(throwable);
            }

            throwable = throwable.getCause();
        }

        return null;
    }
}
