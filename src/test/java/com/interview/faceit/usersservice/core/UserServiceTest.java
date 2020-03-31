package com.interview.faceit.usersservice.core;

import com.interview.faceit.usersservice.core.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private NotificationService notificationService;

  @InjectMocks
  private UserService userService;

  @Test
  public void verify_addUser_and_notify_called_when_add() {
    User user = new User(null, null, null, null, null, null, null);
    when(userRepository.addUser(any())).thenReturn(user);

    userService.addUser(user);

    verify(userRepository).addUser(user);
    verify(notificationService).notifyUserAdded(user);
  }

  @Test
  public void verify_modifyUser_and_notify_called_when_modify() {
    User user = new User(null, null, null, null, null, null, null);
    when(userRepository.modifyUser(any(), any())).thenReturn(user);
    UUID uuid = UUID.randomUUID();

    userService.modifyUser(uuid.toString(), user);

    verify(userRepository).modifyUser(uuid, user);
    verify(notificationService).notifyUserModified(user);
  }

  @Test
  public void verify_removeUser_and_notify_called_when_remove() {
    User user = new User(null, null, null, null, null, null, null);
    when(userRepository.removeUser(any(), any())).thenReturn(user);
    UUID uuid = UUID.randomUUID();

    userService.removeUser(uuid.toString(), "");

    verify(userRepository).removeUser(uuid, "");
    verify(notificationService).notifyUserDeleted(user);
  }

  @Test
  public void given_malformed_uuid_when_removeUser_should_throw_BadRequest() {
    String malformedUuidString = "invalid_uuid";

    assertThrows(BadRequestException.class, () -> {
      userService.removeUser(malformedUuidString, "");
    });
  }
}