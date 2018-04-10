package com.starry.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starry.domain.entity.UserFile;
import com.starry.domain.entity.UserFileSearch;
import com.starry.repository.UserFileESRepository;
import com.starry.repository.UserFileRepository;
import com.starry.service.JestService;
import com.starry.service.interf.IUserFileService;
import io.searchbox.client.JestClient;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private JestClient jestClient;
    @Autowired
    private JestService jestService;


    @Override
    public void saveUserFile(UserFile userFile) {
        userFile = userFileRepository.save(userFile);
        try {
            List arrayList = new ArrayList();
            arrayList.add(userFile);
            jestService.index(jestClient, userFile.getElasticIndexName(), userFile.getElasticTypeName(), arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        UserFileSearch userFileSearch = new UserFileSearch();
//        BeanUtils.copyProperties(userFile, userFileSearch);
//        userFileSearch.setVersion(1l);
//        userFileSearch = userFileESRepository.save(userFileSearch);
//        System.out.println("userFile = [" + userFileSearch.getFileName() + "]");
    }

    @Override
    public List<UserFile> findByFileName(String fileName) {


        return null;
    }

}
