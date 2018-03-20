package com.starry.controller;

import com.starry.domain.entity.UserFile;
import com.starry.domain.jpa.UserFileJPA;
import com.starry.util.FastDFSClientWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 文件管理控制器
 *
 * @author starry
 * @Date 2018-02-18 15:07:58
 */
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/file")
public class FileManageController{
    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;
    @Autowired
    private UserFileJPA userFileJPA;
    /**
     * 上传文件
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile attach) {
        try {
            String path = fastDFSClientWrapper.uploadFile(attach);
            UserFile userFile = new UserFile().setFileName(attach.getOriginalFilename()).setFileSize(attach.getSize() / 1024.00).setId((long) 1).setUid((long) 1).setUrl(path);
            userFileJPA.save(userFile);
        }catch (IOException e){
            log.error("上传文件出错：" + ExceptionUtils.getMessage(e));
        }
        return "SUCCESS";
    }
    /**
     * 下载文件
     */
    @RequestMapping(value = "/download/{fileManageId}")
    @ResponseBody
    public Object  download(@PathVariable("id") Long id) {
        UserFile userFile = userFileJPA.findById(id).orElse(new UserFile());
        byte[] file = fastDFSClientWrapper.downloadFileWithTransmission(userFile.getUrl());
        return file;
    }
}
