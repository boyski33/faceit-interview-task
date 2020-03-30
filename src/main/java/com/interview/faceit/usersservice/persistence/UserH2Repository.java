package com.interview.faceit.usersservice.persistence;

import com.interview.faceit.usersservice.core.User;
import com.interview.faceit.usersservice.core.UserRepository;
import com.interview.faceit.usersservice.core.exceptions.BadRequestException;
import com.interview.faceit.usersservice.core.exceptions.NotFoundException;
import com.interview.faceit.usersservice.persistence.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A specific H2 implementation of the {@link UserRepository} interface.
 * The class is autowired for dependency injection at startup.
 * This keeps the persistence layer abstracted away from the domain (core)
 * layer. This implementation is injected into the constructor of the
 * {@link com.interview.faceit.usersservice.core.UserService} in the domain
 * layer, which calls the interface CRUD methods.
 */
@Repository
public class UserH2Repository implements UserRepository {
  private static String violatedConstraintMessage = "Either nickname or email already exists in database.";

  private UserH2Store store;

  @Autowired
  public UserH2Repository(UserH2Store store) {
    this.store = store;
  }

  @Override
  public User getUserById(UUID id) throws NotFoundException {
    Optional<UserEntity> user = store.findById(id);

    if (user.isEmpty()) {
      throw new NotFoundException();
    }

    return user.get().toDomainObject();
  }

  /**
   * Retrieves a page (list) of users matching the criteria passed as
   * arguments to the method. A {@link Specification} is created,
   * combining multiple specifications for every field of the {@link UserEntity}.
   *
   * @return a {@link Page} object with the {@link User}s retrieved from the database.
   * In case there were no matches, the Page object is empty.
   */
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
  public User addUser(User user) throws BadRequestException {
    try {
      UserEntity persistedUser = store.save(UserEntity.fromDomainObject(user));

      return persistedUser.toDomainObject();
    } catch (DataIntegrityViolationException ex) {
      throw new BadRequestException(violatedConstraintMessage);
    }
  }

  @Override
  public User modifyUser(UUID id, User user) throws NotFoundException, BadRequestException {
    if (!store.existsById(id)) {
      throw new NotFoundException();
    }

    UserEntity e = UserEntity.fromDomainObject(user);
    e.setId(id);

    try {
      return store.save(e).toDomainObject();
    } catch (DataIntegrityViolationException ex) {
      throw new BadRequestException(violatedConstraintMessage);
    }
  }

  /**
   * Deletes a user by UUID or nickname if no UUID provided.
   *
   * @return the deleted user
   * @throws NotFoundException if a user with such ID or nickname doesn't exist
   */
  @Transactional
  @Override
  public User removeUser(UUID id, String nickname) throws NotFoundException {
    List<UserEntity> users = (id == null) ?
        store.removeAllByNickname(nickname) :
        store.removeAllById(id);

    if (users.size() != 1) {
      throw new NotFoundException();
    }
    return users.get(0).toDomainObject();
  }
}
