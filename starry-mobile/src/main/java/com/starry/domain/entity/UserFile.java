package com.starry.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@Table(name = "user_file")
@Entity
public class UserFile {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "uid")
    private Long uid;
    @Column(name = "url")
    private String url;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_size")
    private Double fileSize;

}