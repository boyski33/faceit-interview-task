package com.interview.faceit.usersservice.core.exceptions;

public class UserNotFoundException extends RuntimeException {
  private String userId;

  public UserNotFoundException(String userId) {
    super(String.format("User with ID %s doesn't exist", userId));
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }
}
