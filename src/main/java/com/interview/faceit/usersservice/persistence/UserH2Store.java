package com.interview.faceit.usersservice.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserH2Store extends CrudRepository<UserEntity, UUID> {

  @Override
  boolean existsById(UUID id);

  @Override
  Optional<UserEntity> findById(UUID uuid);

  @Override
  <U extends UserEntity> U save(U user);

  List<UserEntity> removeAllById(UUID id);
}
