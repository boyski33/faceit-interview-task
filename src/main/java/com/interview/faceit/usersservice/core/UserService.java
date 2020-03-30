package com.interview.faceit.usersservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

  public List<User> getUsers(Pageable pageable,
                             String id,
                             String nickname,
                             String firstName,
                             String lastName,
                             String email,
                             String country) {

    UUID uuid = uuidFromString(id);

    Page<User> userPage = userRepository.getUsers(pageable, uuid, nickname, firstName, lastName, email, country);

    if (pageable.getPageNumber() >= userPage.getTotalPages()) {
      // todo 404 or something like "not enough pages"
      throw new UserAintAlrightException();
    }

    return userPage.stream().collect(Collectors.toList());
  }

  public User getUserById(String id) {
    UUID uuid = uuidFromString(id);
    Optional<User> user = userRepository.getUserById(uuid);

    if (user.isEmpty()) {
      // todo throw 404
      throw new RuntimeException();
    }
    return user.get();
  }

  public User addUser(User user) {
    User addedUser = userRepository.addUser(user);
    notificationService.notifyUserAdded(user);

    return addedUser;
  }

  public User modifyUser(String userId, User user) {
    UUID uuid = uuidFromString(userId);

    if (user.getId() != null && !uuid.equals(user.getId())) {
      // todo throw 400 ids don't match
      throw new RuntimeException();
    }

    return userRepository.modifyUser(uuid, user);
  }

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
