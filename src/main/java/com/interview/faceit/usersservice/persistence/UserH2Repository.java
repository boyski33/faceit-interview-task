package com.interview.faceit.usersservice.persistence;

import com.interview.faceit.usersservice.core.User;
import com.interview.faceit.usersservice.core.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
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
    UserEntity persistedUser = store.save(UserEntity.fromDomainObject(user));

    return persistedUser.toDomainObject();
  }

  @Override
  public User modifyUser(User user) {
    return null;
  }

  @Override
  public User removeUser(UUID userId) {
    return null;
  }

}
