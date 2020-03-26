package com.interview.faceit.usersservice.core;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  User addUser(User user);

  User modifyUser(User user);

  User removeUser(UUID userId);
}
