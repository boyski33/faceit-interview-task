package com.interview.faceit.usersservice.core;

import com.interview.faceit.usersservice.core.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

public interface UserRepository {

  User getUserById(UUID id) throws UserNotFoundException;

  Page<User> getUsers(Pageable pageable,
                      UUID id,
                      String nickname,
                      String firstName,
                      String lastName,
                      String email,
                      String country);

  User addUser(User user) throws ConstraintViolationException;

  User modifyUser(UUID id, User user) throws UserNotFoundException, ConstraintViolationException;

  User removeUser(UUID id, String nickname) throws UserNotFoundException;
}
