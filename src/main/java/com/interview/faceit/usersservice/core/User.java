package com.interview.faceit.usersservice.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {
  private final UUID id;
  private final String firstName;
  private final String lastName;
  private final String nickname;
  private final String password;
  private final String email;
  private final String country;

  public User(UUID id,
              String firstName,
              String lastName,
              String nickname,
              String password,
              String email,
              String country) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.nickname = nickname;
    this.password = password;
    this.email = email;
    this.country = country;
  }

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @NotBlank
  public String getNickname() {
    return nickname;
  }

  @NotBlank
  public String getPassword() {
    return password;
  }

  @NotBlank
  @Email
  public String getEmail() {
    return email;
  }

  public String getCountry() {
    return country;
  }

}
