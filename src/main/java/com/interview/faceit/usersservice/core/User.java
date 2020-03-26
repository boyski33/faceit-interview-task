package com.interview.faceit.usersservice.core;

import java.util.UUID;

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

  public String getNickname() {
    return nickname;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getCountry() {
    return country;
  }

}
