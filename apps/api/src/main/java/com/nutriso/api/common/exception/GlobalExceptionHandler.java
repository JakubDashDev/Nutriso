package com.nutriso.api.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.nutriso.api.common.dto.ApiErrorResponse;
import com.nutriso.api.common.dto.FieldValidationErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiErrorResponse> handleResponseStatusException(
    ResponseStatusException exception,
    HttpServletRequest request
  ) {
    return ResponseEntity
      .status(exception.getStatusCode())
      .body(new ApiErrorResponse(
          exception.getStatusCode().value(),
          messageFor(exception)
      ));
  }

  @ExceptionHandler(FieldValidationException.class)
  public ResponseEntity<FieldValidationErrorResponse> handleApiFieldException(
    FieldValidationException exception,
    HttpServletRequest request
  ) {
    return ResponseEntity
      .status(exception.getStatus())
      .body(new FieldValidationErrorResponse(
        exception.getStatus().value(),
        exception.getMessage(),
        exception.getErrors()
      ));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiErrorResponse> handleBadRequestException(
    HttpMessageNotReadableException exception,
    HttpServletRequest request
  ) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(new ApiErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Invalid request body"
      ));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<FieldValidationErrorResponse> handleValidationException(
    MethodArgumentNotValidException exception,
    HttpServletRequest request
  ) {
    Map<String, String> errors = new LinkedHashMap<>();

    for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
      errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(new FieldValidationErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Validation failed",
        errors
      ));
  }

  private String messageFor(ResponseStatusException exception) {
    return exception.getReason() == null ? "Request failed" : exception.getReason();
  }
}
