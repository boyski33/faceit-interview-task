package com.interview.faceit.usersservice.persistence;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserEntityWithNickname implements Specification<UserEntity> {

  private String nickname;

  public UserEntityWithNickname(String nickname) {
    this.nickname = nickname;
  }

  @Override
  public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    if (nickname == null) {
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
    return criteriaBuilder.equal(root.get("nickname"), nickname);
  }

}
