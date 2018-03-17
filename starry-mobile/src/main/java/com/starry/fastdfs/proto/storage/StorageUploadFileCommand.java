package com.starry.fastdfs.proto.storage;

import com.starry.fastdfs.domain.StorePath;
import com.starry.fastdfs.proto.AbstractFdfsCommand;
import com.starry.fastdfs.proto.FdfsResponse;
import com.starry.fastdfs.proto.storage.internal.StorageUploadFileRequest;

import java.io.InputStream;


/**
 * 文件上传命令
 * 
 * @author tobato
 *
 */
public class StorageUploadFileCommand extends AbstractFdfsCommand<StorePath> {

    /**
     * 文件上传命令
     * 
     * @param storeIndex 存储节点
     * @param inputStream 输入流
     * @param fileExtName 文件扩展名
     * @param isAppenderFile 是否支持断点续传
     */
    public StorageUploadFileCommand(byte storeIndex, InputStream inputStream, String fileExtName, long fileSize,
            boolean isAppenderFile) {
        super();
        this.request = new StorageUploadFileRequest(storeIndex, inputStream, fileExtName, fileSize, isAppenderFile);
        // 输出响应
        this.response = new FdfsResponse<StorePath>() {
            // default response
        };
    }

}
