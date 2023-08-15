package com.exsoft.momedumerchant.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface MomEduRepository<U extends Serializable, V extends Serializable>
        extends JpaRepository<U, V>, QuerydslPredicateExecutor<U> {
}
