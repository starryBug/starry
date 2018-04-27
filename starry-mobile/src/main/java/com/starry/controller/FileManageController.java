package com.starry.controller;

import com.starry.domain.entity.UserFile;
import com.starry.domain.jpa.UserFileJPA;
import com.starry.service.JestService;
import com.starry.service.interf.IUserFileService;
import com.starry.util.FastDFSClientWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;


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
    @Autowired
    private IUserFileService userFileService;
    @Autowired
    private JestService jestService;

    /**
     * 上传文件
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Mono<String> upload(MultipartFile attach) {
        try {
            String path = fastDFSClientWrapper.uploadFile(attach);
            UserFile userFile = new UserFile();
            userFile.setFileName(attach.getOriginalFilename())
                    .setFileSize(attach.getSize() / 1024.00)
                    .setUrl(path)
                    .setCreateTime(new Date());
            userFileService.saveUserFile(userFile);
        }catch (Exception e){
            log.error("上传文件出错：" + ExceptionUtils.getMessage(e));
        }finally {
            return Mono.just("SUCCESS");
        }
    }
    /**
     * 查询文件
     */
    @RequestMapping(value = "/es/list", method = RequestMethod.GET)
    @ResponseBody
    public Mono<List> findFileList(HttpServletRequest request) {
        List<UserFile> list = null;
        try {
//            list = userFileService.findByKey(request.getParameter("key"));
            UserFile userFile = new UserFile(){{
                setQuery(request.getParameter("key"));
                setUrl(request.getParameter("url"));
                setFileSizeSort(request.getParameter("orderBy"));
            }};
            list = userFileService.findByUserFile(userFile);
        }catch (Exception e){
            list = Collections.emptyList();
        }
        return Mono.just(list);
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
    @RequestMapping(value = "/test")
    @ResponseBody
    public Mono<String> test() {
        return Mono.just("SUCCESS");
    }

    @RequestMapping(value = "/index/delete/{id}")
    @ResponseBody
    public Mono<String> deleteIdx(@PathVariable("id") String id) throws Exception{
        UserFile userFile = new UserFile();
        jestService.delete(userFile.getElasticIndexName(), userFile.getElasticTypeName(), id);
        return Mono.just("SUCCESS");
    }
}
