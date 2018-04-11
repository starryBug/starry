package com.starry.domain.entity;

import com.starry.elasticsearch.annotation.JestQueryField;
import com.starry.elasticsearch.domain.BaseElasticSearchEntity;
import io.searchbox.annotations.JestId;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Table(name = "user_file")
@Entity
public class UserFile extends BaseElasticSearchEntity implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JestId
    private Long id;
    @Column(name = "uid")
    private Long uid;
    @Column(name = "url")
    private String url;
    @Column(name = "file_name")
    @JestQueryField
    private String fileName;
    @Column(name = "file_size")
    private Double fileSize;

}