package com.interview.faceit.usersservice.persistence.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserEntityWithCountry implements Specification<UserEntity> {

  private String country;

  public UserEntityWithCountry(String country) {
    this.country = country;
  }

  @Override
  public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    if (country == null) {
      return cb.isTrue(cb.literal(true));
    }
    return cb.like(cb.lower(root.get("country")), String.format("%%%s%%", country.toLowerCase()));
  }
}
