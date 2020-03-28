package com.interview.faceit.usersservice.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExceptionHandler {

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UserAintAlrightException.class)
  public ResponseEntity<String> handleUserException() {
    return ResponseEntity.badRequest().body("Shiiiiieeeet");
  }

}
