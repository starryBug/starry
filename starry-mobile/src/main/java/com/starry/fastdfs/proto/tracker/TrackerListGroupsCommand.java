package com.starry.fastdfs.proto.tracker;


import com.starry.fastdfs.domain.GroupState;
import com.starry.fastdfs.proto.AbstractFdfsCommand;
import com.starry.fastdfs.proto.tracker.internal.TrackerListGroupsRequest;
import com.starry.fastdfs.proto.tracker.internal.TrackerListGroupsResponse;

import java.util.List;

/**
 * 列出组命令
 * 
 * @author tobato
 *
 */
public class TrackerListGroupsCommand extends AbstractFdfsCommand<List<GroupState>> {

    public TrackerListGroupsCommand() {
        super.request = new TrackerListGroupsRequest();
        super.response = new TrackerListGroupsResponse();
    }

}
