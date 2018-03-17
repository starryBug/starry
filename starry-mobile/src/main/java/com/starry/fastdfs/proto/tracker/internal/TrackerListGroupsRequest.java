package com.starry.fastdfs.proto.tracker.internal;


import com.starry.fastdfs.proto.CmdConstants;
import com.starry.fastdfs.proto.FdfsRequest;
import com.starry.fastdfs.proto.ProtoHead;

/**
 * 列出分组命令
 * 
 * @author tobato
 *
 */
public class TrackerListGroupsRequest extends FdfsRequest {

    public TrackerListGroupsRequest() {
        head = new ProtoHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_LIST_GROUP);
    }
}
