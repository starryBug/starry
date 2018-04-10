package com.starry.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserFileSearch{
    private String id;
    private String url;
    private String fileName;
}