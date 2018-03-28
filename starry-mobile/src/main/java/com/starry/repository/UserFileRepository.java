package com.starry.repository;

import com.starry.domain.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Created by IBA-EDV on 2018/3/28.
 */
public interface UserFileRepository extends JpaRepository<UserFile, Long> {

}
