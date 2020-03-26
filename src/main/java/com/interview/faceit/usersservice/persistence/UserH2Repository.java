package com.interview.faceit.usersservice.persistence;

import com.interview.faceit.usersservice.core.User;
import com.interview.faceit.usersservice.core.UserRepository;
import org.hibernate.persister.spi.UnknownPersisterException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserH2Repository implements UserRepository {


  @Override
  public User addUser(User user) {
    return null;
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
