package com.nutriso.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(
        ResponseStatusException exception,
        HttpServletRequest request
    ) {
        return ResponseEntity
            .status(exception.getStatusCode())
            .body(new ApiError(
                exception.getStatusCode().value(),
                messageFor(exception),
                request.getRequestURI()
            ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBadRequestException(
        HttpMessageNotReadableException exception,
        HttpServletRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request body",
                request.getRequestURI()
            ));
    }

    private String messageFor(ResponseStatusException exception) {
        return exception.getReason() == null ? "Request failed" : exception.getReason();
    }
}
