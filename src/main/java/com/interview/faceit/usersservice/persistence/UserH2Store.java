package com.interview.faceit.usersservice.persistence;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserH2Store extends CrudRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

  @Override
  boolean existsById(UUID id);

  @Override
  Optional<UserEntity> findById(UUID uuid);

  List<UserEntity> findAll(Specification<UserEntity> spec);

  @Override
  <U extends UserEntity> U save(U user);

  List<UserEntity> removeAllById(UUID id);

  List<UserEntity> removeAllByNickname(String nickname);

}
