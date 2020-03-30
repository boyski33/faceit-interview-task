package com.interview.faceit.usersservice.core.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super("User doesn't exist.");
  }

}
