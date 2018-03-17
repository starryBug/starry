package com.starry.fastdfs.proto.tracker;


import com.starry.fastdfs.domain.StorageNodeInfo;
import com.starry.fastdfs.proto.AbstractFdfsCommand;
import com.starry.fastdfs.proto.FdfsResponse;
import com.starry.fastdfs.proto.tracker.internal.TrackerGetFetchStorageRequest;

/**
 * 获取源服务器
 * 
 * @author tobato
 *
 */
public class TrackerGetFetchStorageCommand extends AbstractFdfsCommand<StorageNodeInfo> {

    public TrackerGetFetchStorageCommand(String groupName, String path, boolean toUpdate) {
        super.request = new TrackerGetFetchStorageRequest(groupName, path, toUpdate);
        super.response = new FdfsResponse<StorageNodeInfo>() {
            // default response
        };
    }

}
