package com.starry.domain.jpa;

import com.starry.domain.entity.User;
import com.starry.domain.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by IBA-EDV on 2018/3/14.
 */
public interface UserFileJPA extends Serializable,JpaRepository<UserFile, Long>,JpaSpecificationExecutor<UserFile> {
}
