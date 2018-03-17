package com.starry.fastdfs.proto.storage;


import com.starry.fastdfs.domain.MateData;
import com.starry.fastdfs.proto.AbstractFdfsCommand;
import com.starry.fastdfs.proto.storage.internal.StorageGetMetadataRequest;
import com.starry.fastdfs.proto.storage.internal.StorageGetMetadataResponse;

import java.util.Set;

/**
 * 设置文件标签
 * 
 * @author tobato
 *
 */
public class StorageGetMetadataCommand extends AbstractFdfsCommand<Set<MateData>> {

    /**
     * 设置文件标签(元数据)
     * 
     * @param groupName
     * @param path
     */
    public StorageGetMetadataCommand(String groupName, String path) {
        this.request = new StorageGetMetadataRequest(groupName, path);
        // 输出响应
        this.response = new StorageGetMetadataResponse();
    }

}
