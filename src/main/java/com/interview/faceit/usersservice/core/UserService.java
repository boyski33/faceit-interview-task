package com.interview.faceit.usersservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

  private UserRepository userRepository;
  private NotificationService notificationService;

  @Autowired
  public UserService(UserRepository userRepository,
                     NotificationService notificationService) {
    this.userRepository = userRepository;
    this.notificationService = notificationService;
  }

  public User addUser(User user) {
    User addedUser = userRepository.addUser(user);
    notificationService.notifyUserAdded(user);

    return addedUser;
  }

//  public User modifyUser(User user) {
//
//  }

  public User removeUser(String userId, String nickname) {
    User deletedUser = userRepository.removeUser(userId, nickname);
    notificationService.notifyUserDeleted(deletedUser);

    return deletedUser;
  }

//  public List<User> queryUsers() {
// todo
//  }
}
