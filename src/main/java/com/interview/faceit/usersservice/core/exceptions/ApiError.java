package com.interview.faceit.usersservice.core.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

  private HttpStatus status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private String message;

  private ApiError(HttpStatus status) {
    this.status = status;
    this.timestamp = LocalDateTime.now();
    this.message = "Unknown error";
  }

  private ApiError(HttpStatus status, String message) {
    this(status);
    this.message = message;
  }

  public static ApiError errorWithStatus(HttpStatus status) {
    return new ApiError(status);
  }

  public static ApiError errorWithStatusAndMessage(HttpStatus status, String message) {
    return new ApiError(status, message);
  }
}
