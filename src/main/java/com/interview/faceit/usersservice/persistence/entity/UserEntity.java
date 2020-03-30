package com.interview.faceit.usersservice.persistence.entity;

import com.interview.faceit.usersservice.core.User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * A JPA entity representing the User class to be persisted in the database.
 */
@Entity
@Table(name = "users")
public class UserEntity {
  private UUID id;
  private String firstName;
  private String lastName;
  private String nickname;
  private String password;
  private String email;
  private String country;

  public UserEntity() {
  }

  public UserEntity(UUID id,
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

  @Id
  @GeneratedValue
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Column(name = "first_name")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "last_name")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(name = "nickname", nullable = false, unique = true)
  @NotBlank
  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  @Column(name = "password_hash", nullable = false)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "email", nullable = false, unique = true)
  @Email
  @NotBlank
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "country", nullable = false)
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public static UserEntity fromDomainObject(User user) {
    return new UserEntity(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getNickname(),
        user.getPassword(),
        user.getEmail(),
        user.getCountry()
    );
  }

  public User toDomainObject() {
    return new User(
        id,
        firstName,
        lastName,
        nickname,
        password,
        email,
        country
    );
  }
}
