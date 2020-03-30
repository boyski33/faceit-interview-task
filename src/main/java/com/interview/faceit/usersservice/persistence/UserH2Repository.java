package com.interview.faceit.usersservice.persistence;

import com.interview.faceit.usersservice.core.User;
import com.interview.faceit.usersservice.core.exceptions.UserNotFoundException;
import com.interview.faceit.usersservice.core.UserRepository;
import com.interview.faceit.usersservice.persistence.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserH2Repository implements UserRepository {
  private static String violatedConstraintMessage = "Either nickname or email already exists in database.";

  private UserH2Store store;

  @Autowired
  public UserH2Repository(UserH2Store store) {
    this.store = store;
  }

  @Override
  public User getUserById(UUID id) throws UserNotFoundException {
    Optional<UserEntity> user = store.findById(id);

    if (user.isEmpty()) {
      throw new UserNotFoundException(id.toString());
    }

    return user.get().toDomainObject();
  }

  @Override
  public Page<User> getUsers(Pageable pageable,
                             UUID id,
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

    return store.findAll(userSpec, pageable).map(UserEntity::toDomainObject);
  }

  @Override
  public User addUser(User user) throws ConstraintViolationException {
    try {
      UserEntity persistedUser = store.save(UserEntity.fromDomainObject(user));

      return persistedUser.toDomainObject();
    } catch (DataIntegrityViolationException ex) {
      throw new ConstraintViolationException(violatedConstraintMessage, null);
    }
  }

  @Override
  public User modifyUser(UUID id, User user) throws UserNotFoundException, ConstraintViolationException {
    if (!store.existsById(id)) {
      throw new UserNotFoundException("modify error");
    }

    UserEntity e = UserEntity.fromDomainObject(user);
    e.setId(id);

    try {
      return store.save(e).toDomainObject();
    } catch (DataIntegrityViolationException ex) {
      throw new ConstraintViolationException(violatedConstraintMessage, null);
    }
  }

  @Transactional
  @Override
  public User removeUser(UUID userId, String nickname) throws UserNotFoundException {
    List<UserEntity> users = (userId == null) ?
        store.removeAllByNickname(nickname) :
        store.removeAllById(userId);

    if (users.size() != 1) {
      throw new UserNotFoundException("remove error");
    }
    return users.get(0).toDomainObject();
  }
}
