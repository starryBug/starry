package com.starry.repository;

import com.starry.domain.entity.UserFileSearch;

import java.util.List;


/**
 * Created by IBA-EDV on 2018/3/28.
 */
public interface UserFileESRepository {
//public interface UserFileESRepository extends ElasticsearchRepository<UserFileSearch, Long> {
    /**
     * 名称查询
     *
     * @param fileName
     * @return
     */
    List<UserFileSearch> findByFileName(String fileName);

}
