package com.starry.fastdfs.proto.storage;


import com.starry.fastdfs.proto.AbstractFdfsCommand;
import com.starry.fastdfs.proto.FdfsResponse;
import com.starry.fastdfs.proto.storage.internal.StorageDeleteFileRequest;

/**
 * 文件删除命令
 * 
 * @author tobato
 *
 */
public class StorageDeleteFileCommand extends AbstractFdfsCommand<Void> {

    /**
     * 文件删除命令
     * 
     * @param groupName
     * @param path
     */
    public StorageDeleteFileCommand(String groupName, String path) {
        super();
        this.request = new StorageDeleteFileRequest(groupName, path);
        // 输出响应
        this.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
