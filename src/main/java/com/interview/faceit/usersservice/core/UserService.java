package com.interview.faceit.usersservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

  public List<User> getUsers(String id,
                             String nickname,
                             String firstName,
                             String lastName,
                             String email,
                             String country) {

    UUID uuid = uuidFromString(id);

    return userRepository.getUsers(uuid, nickname, firstName, lastName, email, country);
  }

  public User addUser(User user) {
    User addedUser = userRepository.addUser(user);
    notificationService.notifyUserAdded(user);

    return addedUser;
  }

//  public User modifyUser(User user) {
//
//  }

  public User removeUser(String id, String nickname) {
    UUID uuid = uuidFromString(id);

    User deletedUser = userRepository.removeUser(uuid, nickname);
    notificationService.notifyUserDeleted(deletedUser);

    return deletedUser;
  }

  private static UUID uuidFromString(String id) {
    if (id == null) {
      return null;
    }

    // todo handle format exception
    return UUID.fromString(id);
  }

}
