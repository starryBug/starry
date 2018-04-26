package com.starry.service.interf;

import com.starry.domain.entity.UserFile;

import java.util.List;

/**
 * Created by IBA-EDV on 2018/3/28.
 */
public interface IUserFileService {
    void saveUserFile(UserFile userFile);
    List<UserFile> findByKey(String qryKey);
    List<UserFile> findByUserFile(UserFile userFile);
}
