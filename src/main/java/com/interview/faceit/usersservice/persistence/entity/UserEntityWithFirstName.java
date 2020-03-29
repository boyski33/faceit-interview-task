package com.interview.faceit.usersservice.persistence.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserEntityWithFirstName implements Specification<UserEntity> {

  private String firstName;

  public UserEntityWithFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    if (firstName == null) {
      return cb.isTrue(cb.literal(true));
    }
    return cb.like(cb.lower(root.get("firstName")), String.format("%%%s%%", firstName.toLowerCase()));
  }
}
