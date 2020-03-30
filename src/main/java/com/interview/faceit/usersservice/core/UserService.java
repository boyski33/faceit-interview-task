package com.interview.faceit.usersservice.core;

import com.interview.faceit.usersservice.core.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    int pageNumber = pageable.getPageNumber();
    int totalPages = userPage.getTotalPages();

    if (pageNumber > 0 && pageNumber >= totalPages) {
      String errMsg = String.format("Page %d out of range. Total number of pages is %d.", pageNumber, totalPages);
      throw new BadRequestException(errMsg);
    }

    return userPage.stream().collect(Collectors.toList());
  }

  public User getUserById(String id) {
    UUID uuid = uuidFromString(id);

    return userRepository.getUserById(uuid);
  }

  public User addUser(User user) {
    User addedUser = userRepository.addUser(user);
    notificationService.notifyUserAdded(user);

    return addedUser;
  }

  public User modifyUser(String userId, User user) {
    UUID uuid = uuidFromString(userId);

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

    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException ex) {
      String errMsg = String.format("ID: %s isn't a valid UUID format.", id);
      throw new BadRequestException(errMsg);
    }
  }

}
