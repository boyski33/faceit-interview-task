package com.interview.faceit.usersservice.core;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

  List<User> getUsers(UUID id, String nickname, String firstName, String lastName, String email, String country);

  User addUser(User user);

  User modifyUser(User user);

  User removeUser(UUID id, String nickname);
}
