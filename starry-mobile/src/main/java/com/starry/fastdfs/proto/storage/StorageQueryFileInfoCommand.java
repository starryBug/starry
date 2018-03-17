package com.starry.fastdfs.proto.storage;


import com.starry.fastdfs.domain.FileInfo;
import com.starry.fastdfs.proto.AbstractFdfsCommand;
import com.starry.fastdfs.proto.FdfsResponse;
import com.starry.fastdfs.proto.storage.internal.StorageQueryFileInfoRequest;

/**
 * 文件删除命令
 * 
 * @author tobato
 *
 */
public class StorageQueryFileInfoCommand extends AbstractFdfsCommand<FileInfo> {

    /**
     * 文件上传命令
     * 
     * @param groupName
     * @param path
     */
    public StorageQueryFileInfoCommand(String groupName, String path) {
        super();
        this.request = new StorageQueryFileInfoRequest(groupName, path);
        // 输出响应
        this.response = new FdfsResponse<FileInfo>() {
            // default response
        };
    }

}
