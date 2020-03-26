package com.interview.faceit.usersservice.core;

import java.util.UUID;

public interface UserRepository {

  User getUserById(UUID id);

  User addUser(User user);

  User modifyUser(User user);

  User removeUser(UUID userId);
}
