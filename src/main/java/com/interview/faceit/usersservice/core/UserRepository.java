package com.interview.faceit.usersservice.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  Optional<User> getUserById(UUID id);

  Page<User> getUsers(Pageable pageable,
                      UUID id,
                      String nickname,
                      String firstName,
                      String lastName,
                      String email,
                      String country);

  User addUser(User user);

  User modifyUser(UUID id, User user);

  User removeUser(UUID id, String nickname);
}
