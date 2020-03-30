package com.interview.faceit.usersservice.persistence;

import com.interview.faceit.usersservice.core.User;
import com.interview.faceit.usersservice.core.UserAintAlrightException;
import com.interview.faceit.usersservice.core.UserRepository;
import com.interview.faceit.usersservice.persistence.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserH2Repository implements UserRepository {
  private UserH2Store store;

  @Autowired
  public UserH2Repository(UserH2Store store) {
    this.store = store;
  }

  @Override
  public Optional<User> getUserById(UUID id) {
    return store.findById(id).map(UserEntity::toDomainObject);
  }

  @Override
  public List<User> getUsers(UUID id,
                             String nickname,
                             String firstName,
                             String lastName,
                             String email,
                             String country) {

    Specification<UserEntity> userSpec = Specification
        .where(new UserEntityWithId(id))
        .and(new UserEntityWithNickname(nickname))
        .and(new UserEntityWithFirstName(firstName))
        .and(new UserEntityWithLastName(lastName))
        .and(new UserEntityWithEmail(email))
        .and(new UserEntityWithCountry(country));

    return store.findAll(userSpec).stream()
        .map(UserEntity::toDomainObject)
        .collect(Collectors.toList());
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
  public User modifyUser(UUID id, User user) {
    if (!store.existsById(id)) {
      // todo throw user doesn't exist to be updated
      throw new UserPersistenceException();
    }

    UserEntity e = UserEntity.fromDomainObject(user);
    e.setId(id);

    return store.save(e).toDomainObject();
  }

  @Override
  public User removeUser(UUID userId, String nickname) {
    List<UserEntity> users = (userId == null) ?
        store.removeAllByNickname(nickname) :
        store.removeAllById(userId);

    if (users.size() != 1) {
      throw new UserPersistenceException();
    }
    return users.get(0).toDomainObject();
  }
}
