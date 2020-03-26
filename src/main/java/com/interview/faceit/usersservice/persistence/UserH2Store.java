package com.interview.faceit.usersservice.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface UserH2Store extends CrudRepository<UserEntity, UUID> {
}
