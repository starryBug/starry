package com.starry.domain.mapper;

import com.starry.domain.entity.UserFile;

public interface UserFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFile record);

    int insertSelective(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFile record);

    int updateByPrimaryKey(UserFile record);
}