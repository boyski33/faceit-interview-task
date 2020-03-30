package com.interview.faceit.usersservice.core;

import com.interview.faceit.usersservice.core.exceptions.BadRequestException;
import com.interview.faceit.usersservice.core.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * An interface to be implemented by a concrete class in the repository layer.
 * This is following the Hexagonal architecture (or the Ports and Adapters design pattern).
 */
public interface UserRepository {

  User getUserById(UUID id) throws NotFoundException;

  Page<User> getUsers(Pageable pageable,
                      UUID id,
                      String nickname,
                      String firstName,
                      String lastName,
                      String email,
                      String country);

  User addUser(User user) throws BadRequestException;

  User modifyUser(UUID id, User user) throws NotFoundException, BadRequestException;

  User removeUser(UUID id, String nickname) throws NotFoundException;
}
