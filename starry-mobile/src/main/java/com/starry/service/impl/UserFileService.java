package com.starry.service.impl;
import com.alibaba.fastjson.JSON;
import com.starry.domain.entity.UserFile;
import com.starry.repository.UserFileRepository;
import com.starry.service.JestService;
import com.starry.service.interf.IUserFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by IBA-EDV on 2018/3/28.
 */
@Slf4j
@Service
public class UserFileService implements IUserFileService{
    @Autowired
    private UserFileRepository userFileRepository;
    @Autowired
    private JestService jestService;


    @Override
    public void saveUserFile(UserFile userFile) {
        userFile = userFileRepository.save(userFile);
        try {
            jestService.index(userFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserFile> findByFileName(String fileName){
        return jestService.search(new UserFile().setFileName(fileName));
    }

}
