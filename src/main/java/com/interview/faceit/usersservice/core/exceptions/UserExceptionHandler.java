package com.interview.faceit.usersservice.core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * An exception handler class shared between Spring Controller classes.
 * If a specified exception is propagated to the Controller level, an
 * {@link ApiError} response is created and returned to the client.
 */
@ControllerAdvice
public class UserExceptionHandler {

  private static final Logger LOGGER = LogManager.getLogger(UserExceptionHandler.class);

  /**
   * If a NotFoundException is caught, logs it and returns an
   * {@link ApiError} response with a status of 404 NOT FOUND,
   * and the exception's message.
   *
   * @param ex the caught exception
   * @return a {@link ResponseEntity} with the error as payload
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
    ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

    LOGGER.error(ex);

    return new ResponseEntity<>(error, error.getStatus());
  }

  /**
   * If a BadRequestException is caught, logs it and returns an
   * {@link ApiError} response with a status of 400 BAD REQUEST,
   * and the exception's message.
   *
   * @param ex the caught exception
   * @return a {@link ResponseEntity} with the error as payload
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiError> handleConstraintViolationException(BadRequestException ex) {
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    LOGGER.error(ex);

    return new ResponseEntity<>(error, error.getStatus());
  }

}
