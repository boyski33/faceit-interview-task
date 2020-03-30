package com.interview.faceit.usersservice.persistence;

import com.interview.faceit.usersservice.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Override
  Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);

  @Override
  <U extends UserEntity> U save(U user);

  List<UserEntity> removeAllById(UUID id);

  List<UserEntity> removeAllByNickname(String nickname);

}
