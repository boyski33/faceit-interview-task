package com.interview.faceit.usersservice.persistence.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserEntityWithLastName implements Specification<UserEntity> {

  private String lastName;

  public UserEntityWithLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    if (lastName == null) {
      return cb.isTrue(cb.literal(true));
    }
    return cb.like(cb.lower(root.get("lastName")), String.format("%%%s%%", lastName.toLowerCase()));
  }
}
