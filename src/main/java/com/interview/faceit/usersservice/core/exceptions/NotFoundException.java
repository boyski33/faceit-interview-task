package com.interview.faceit.usersservice.core.exceptions;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("User doesn't exist.");
  }
}
