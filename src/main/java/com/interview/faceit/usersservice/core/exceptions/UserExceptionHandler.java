package com.interview.faceit.usersservice.core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class UserExceptionHandler {

  private static final Logger LOGGER = LogManager.getLogger(UserExceptionHandler.class);

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFoundException(UserNotFoundException ex) {
    ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

    LOGGER.error(ex);

    return new ResponseEntity<>(error, error.getStatus());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    LOGGER.error(ex);

    return new ResponseEntity<>(error, error.getStatus());
  }

}
