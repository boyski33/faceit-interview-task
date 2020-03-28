package com.interview.faceit.usersservice.persistence;

import com.interview.faceit.usersservice.core.User;
import com.interview.faceit.usersservice.core.UserAintAlrightException;
import com.interview.faceit.usersservice.core.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class UserH2Repository implements UserRepository {
  private UserH2Store store;

  @Autowired
  public UserH2Repository(UserH2Store store) {
    this.store = store;
  }

  @Override
  public User getUserById(UUID id) {
    return null;
  }

  @Override
  public User addUser(User user) {
    try {
      UserEntity persistedUser = store.save(UserEntity.fromDomainObject(user));
      return persistedUser.toDomainObject();
    } catch (ConstraintViolationException e) {
      throw new UserAintAlrightException();
    }
  }

  @Override
  public User modifyUser(User user) {
    return null;
  }

  @Override
  public User removeUser(UUID userId) {
    List<UserEntity> users = store.removeAllById(userId);

    if (users.size() != 1) {
      throw new UserPersistenceException();
    }

    return users.get(0).toDomainObject();
  }
}
