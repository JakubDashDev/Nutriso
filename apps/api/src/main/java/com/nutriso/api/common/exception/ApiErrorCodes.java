package com.nutriso.api.common.exception;

public class ApiErrorCodes {
  public static final String REQUIRED = "REQUIRED";
  public static final String TOO_LONG = "TOO_LONG";
  public static final String TOO_SHORT = "TOO_SHORT";
  public static final String MUST_BE_POSITIVE = "MUST_BE_POSITIVE";
  public static final String MUST_BE_POSITIVE_OR_ZERO = "MUST_BE_POSITIVE_OR_ZERO";
  public static final String INVALID_FORMAT = "INVALID_FORMAT";
  public static final String INVALID_EMAIL = "INVALID_EMAIL";
  public static final String INVALID_DATE_RANGE = "INVALID_DATE_RANGE";
  public static final String START_DATE_BEFORE_END_DATE = "START_DATE_BEFORE_END_DATE";
  public static final String END_DATE_BEFORE_START_DATE = "END_DATE_BEFORE_START_DATE";
  public static final String ALREADY_EXISTS = "ALREADY_EXISTS";
  public static final String NOT_FOUND = "NOT_FOUND";
  public static final String EXPIRED = "EXPIRED";
  public static final String INVALID_TYPE = "INVALID_TYPE";
  public static final String PASSWORD_DO_NOT_MATCH = "PASSWORD_DO_NOT_MATCH";

  public static final String TOO_LONG_MAX = TOO_LONG + "_{max}";
  public static final String TOO_SHORT_MIN = TOO_SHORT + "_{min}";

  public static final String VALIDATION_FAILED = "VALIDATION_FAILED";
  public static final String INVALID_REQUEST_BODY = "INVALID_REQUEST_BODY";
}
