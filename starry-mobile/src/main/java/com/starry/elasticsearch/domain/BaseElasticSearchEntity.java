package com.starry.elasticsearch.domain;

import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public abstract class BaseElasticSearchEntity implements Serializable {
    /**
     * 类似数据库
     */
    protected String elasticIndexName;
    /**
     * 类似数据库中的表
     */
    protected String elasticTypeName;

    public void setElasticIndexName(String elasticIndexName) {
        this.elasticIndexName = elasticIndexName;
    }

    public void setElasticTypeName(String elasticTypeName) {
        this.elasticTypeName = elasticTypeName;
    }

    public String getElasticIndexName() {
        if (isEmpty(elasticIndexName)) {
            this.elasticIndexName = "starry";
        }
        return this.elasticIndexName;

    }

    public String getElasticTypeName() {
        if (isEmpty(elasticTypeName)) {
            this.elasticTypeName = this.getClass().getSimpleName();
        }
        return this.elasticTypeName;
    }
}
