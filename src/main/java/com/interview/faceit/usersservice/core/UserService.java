package com.interview.faceit.usersservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User addUser(User user) {

  }

  public User modifyUser(User user) {

  }

  public User removeUser(UUID userId) {

  }

//  public List<User> queryUsers() {
// todo
//  }
}
