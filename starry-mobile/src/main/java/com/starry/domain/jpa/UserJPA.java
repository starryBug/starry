package com.starry.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by IBA-EDV on 2018/3/14.
 */
public interface UserJPA extends Serializable,JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
}
