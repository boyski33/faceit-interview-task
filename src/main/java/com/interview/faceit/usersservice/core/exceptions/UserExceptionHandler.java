package com.interview.faceit.usersservice.core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

  private static final Logger LOGGER = LogManager.getLogger(UserExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
    ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

    LOGGER.error(ex);

    return new ResponseEntity<>(error, error.getStatus());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiError> handleConstraintViolationException(BadRequestException ex) {
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    LOGGER.error(ex);

    return new ResponseEntity<>(error, error.getStatus());
  }

}
