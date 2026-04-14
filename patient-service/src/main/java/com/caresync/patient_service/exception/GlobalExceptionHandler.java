package com.caresync.patient_service.exception;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import com.caresync.patient_service.validation.PatientErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(
      GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException .class)
  public ResponseEntity<Map<String, String>> handleValidationException(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(
        error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(
      EmailAlreadyExistsException ex) {

    log.warn("Email already exists: {}", ex.getMessage());
    Map<String, String> errors = new HashMap<>();
    errors.put(PatientErrorMessage.ERROR_MESSAGE_KEY,
        getMessageOrDefault(ex.getMessage(), PatientErrorMessage.EMAIL_ALREADY_EXISTS));
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(PatientNotFoundException.class)
  public ResponseEntity<Map<String, String>> handlePatientNotFoundException(
      PatientNotFoundException ex) {
    log.warn("Patient not found: {}", ex.getMessage());

    Map<String, String> errors = new HashMap<>();
    errors.put(PatientErrorMessage.ERROR_MESSAGE_KEY,
        getMessageOrDefault(ex.getMessage(), PatientErrorMessage.PATIENT_NOT_FOUND));
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<Map<String, String>> handleDateTimeParseException(
      DateTimeParseException ex) {
    log.warn("Invalid patient date value: {}", ex.getParsedString());

    Map<String, String> errors = new HashMap<>();
    errors.put(PatientErrorMessage.ERROR_MESSAGE_KEY,
        PatientErrorMessage.DATE_OF_BIRTH_INVALID);
    return ResponseEntity.badRequest().body(errors);
  }

  private String getMessageOrDefault(String message, String defaultMessage) {
    return StringUtils.hasText(message) ? message : defaultMessage;
  }
}
