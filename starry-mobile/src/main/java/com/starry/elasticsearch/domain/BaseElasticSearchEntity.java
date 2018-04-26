package com.starry.elasticsearch.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.isEmpty;
@Data
@Accessors(chain = true)
public abstract class BaseElasticSearchEntity implements Serializable {
    /**
     * 类似数据库
     */
    protected transient String elasticIndexName;
    /**
     * 类似数据库中的表
     */
    protected transient String elasticTypeName;
    /**
     * 查询关键字
     */
    public transient String query;

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
