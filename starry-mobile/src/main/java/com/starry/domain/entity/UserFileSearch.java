package com.starry.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@Accessors(chain = true)
@Document(indexName = "userfile",type = "userfile" , shards = 1, replicas = 0, indexStoreType = "memory", refreshInterval = "-1")
public class UserFileSearch{
    @Id
    private Long id;
    private Long uid;
    private String url;
    private String fileName;
    private Double fileSize;
    @Version
    private Long version;
}