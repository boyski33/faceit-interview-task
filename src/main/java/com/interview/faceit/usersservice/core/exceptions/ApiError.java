package com.interview.faceit.usersservice.core.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

  private HttpStatus status;
  private LocalDateTime timestamp;
  private String message;

  ApiError(HttpStatus status) {
    this.status = status;
    this.timestamp = LocalDateTime.now();
    this.message = "Unknown error";
  }

  ApiError(HttpStatus status, String message) {
    this(status);
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }
}
