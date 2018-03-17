package com.starry.fastdfs.proto.tracker;


import com.starry.fastdfs.domain.StorageState;
import com.starry.fastdfs.proto.AbstractFdfsCommand;
import com.starry.fastdfs.proto.tracker.internal.TrackerListStoragesRequest;
import com.starry.fastdfs.proto.tracker.internal.TrackerListStoragesResponse;

import java.util.List;

/**
 * 列出组命令
 * 
 * @author tobato
 *
 */
public class TrackerListStoragesCommand extends AbstractFdfsCommand<List<StorageState>> {

    public TrackerListStoragesCommand(String groupName, String storageIpAddr) {
        super.request = new TrackerListStoragesRequest(groupName, storageIpAddr);
        super.response = new TrackerListStoragesResponse();
    }

    public TrackerListStoragesCommand(String groupName) {
        super.request = new TrackerListStoragesRequest(groupName);
        super.response = new TrackerListStoragesResponse();
    }

}
