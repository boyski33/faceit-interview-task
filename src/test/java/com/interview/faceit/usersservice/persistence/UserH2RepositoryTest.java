package com.interview.faceit.usersservice.persistence;

import com.interview.faceit.usersservice.core.User;
import com.interview.faceit.usersservice.core.exceptions.BadRequestException;
import com.interview.faceit.usersservice.core.exceptions.NotFoundException;
import com.interview.faceit.usersservice.persistence.entity.UserEntity;
import org.apache.tomcat.util.buf.UEncoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserH2RepositoryTest {

  @Mock
  private UserH2Store store;

  @InjectMocks
  private UserH2Repository repository;

  @Test
  public void given_existing_user_in_db_when_getById_should_return_user() {
    UUID uuid = UUID.randomUUID();
    UserEntity entity = new UserEntity();
    entity.setId(uuid);
    entity.setFirstName("Boyan");

    when(store.findById(uuid)).thenReturn(Optional.of(entity));

    User user = repository.getUserById(uuid);

    assertEquals(uuid, user.getId());
    assertEquals("Boyan", user.getFirstName());
  }

  @Test
  public void given_not_existing_user_when_getById_should_throw_NotFound() {
    UUID uuid = UUID.randomUUID();

    when(store.findById(uuid)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> {
      repository.getUserById(uuid);
    });
  }

  @Test
  public void given_violated_constraint_when_add_should_throw_BadRequest() {
    User user = new User(null, null, null, null, null, null, null);

    when(store.save(any())).thenThrow(DataIntegrityViolationException.class);

    assertThrows(BadRequestException.class, () -> {
      repository.addUser(user);
    });
  }

  @Test
  public void given_user_with_id_doesnt_exist_when_modify_should_throw_NotFound() {
    User user = new User(null, null, null, null, null, null, null);

    when(store.existsById(any())).thenReturn(false);

    assertThrows(NotFoundException.class, () -> {
      repository.modifyUser(any(), user);
    });
  }

  @Test
  public void given_violated_constraint_when_modify_should_throw_BadRequest() {
    User user = new User(null, null, null, null, null, null, null);

    when(store.existsById(any())).thenReturn(true);
    when(store.save(any())).thenThrow(DataIntegrityViolationException.class);

    assertThrows(BadRequestException.class, () -> {
      repository.modifyUser(any(), user);
    });
  }

  @Test
  public void given_id_and_nickname_when_remove_should_remove_user_by_id() {
    UUID id = UUID.randomUUID();
    UserEntity entity = new UserEntity();
    when(store.removeAllById(id)).thenReturn(List.of(entity));

    User user = repository.removeUser(id, "boyski");

    verify(store).removeAllById(id);
  }

  @Test
  public void given_only_nickname_when_remove_should_remove_by_nickname() {
    UserEntity entity = new UserEntity();
    when(store.removeAllByNickname(any())).thenReturn(List.of(entity));

    User user = repository.removeUser(null, "boyski");

    verify(store).removeAllByNickname("boyski");
  }

  @Test
  public void given_no_user_in_database_when_remove_should_throw_NotFound() {
    when(store.removeAllById(any())).thenReturn(Collections.emptyList());

    assertThrows(NotFoundException.class, () -> {
      repository.removeUser(UUID.randomUUID(), null);
    });
  }
}