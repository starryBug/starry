package com.starry.service.impl;

import com.starry.domain.entity.UserFile;
import com.starry.domain.entity.UserFileSearch;
import com.starry.repository.UserFileESRepository;
import com.starry.repository.UserFileRepository;
import com.starry.service.interf.IUserFileService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.functionScoreQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.exponentialDecayFunction;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.randomFunction;

/**
 * Created by IBA-EDV on 2018/3/28.
 */
@Slf4j
@Service
public class UserFileService implements IUserFileService{
    @Autowired
    private UserFileESRepository userFileESRepository;
    @Autowired
    private UserFileRepository userFileRepository;
    /* 搜索模式 */
    String SCORE_MODE_SUM = "sum"; // 权重分求和模式
    Float  MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

    @Override
    public void saveUserFile(UserFile userFile) {
        userFile = userFileRepository.save(userFile);
        UserFileSearch userFileSearch = new UserFileSearch();
        BeanUtils.copyProperties(userFile, userFileSearch);
        userFileSearch.setVersion(1l);
        userFileSearch = userFileESRepository.save(userFileSearch);
        System.out.println("userFile = [" + userFileSearch.getFileName() + "]");
    }

    @Override
    public List<UserFile> findByFileName(String fileName) {

        log.info("\n searchCity: searchContent [" + fileName + "] \n ");
        // 构建搜索查询
        SearchQuery searchQuery = getFileSearchQuery(1, 20, fileName);
        log.info("\n searchCity: searchContent [" + fileName + "] \n DSL  = \n " + searchQuery.getQuery().toString());
        //Page<UserFile> filePage = userFileRepository.search(searchQuery);
        //return filePage.getContent();
        return null;
    }

    private SearchQuery getFileSearchQuery(Integer pageNumber, Integer pageSize, String searchContent) {
        // 短语匹配到的搜索词，求和模式累加权重分
        // 权重分查询 https://www.elastic.co/guide/c ... .html
        //   - 短语匹配 https://www.elastic.co/guide/c ... .html
        //   - 字段对应权重分设置，可以优化成 enum
        //   - 由于无相关性的分值默认为 1 ，设置权重分最小值为 10
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        matchQuery("name", "kimchy"),
                        randomFunction(10)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        exponentialDecayFunction("age", 0L, 1L))
        };
        FunctionScoreQueryBuilder functionScoreQueryBuilder = functionScoreQuery(functions);
        // 分页参数
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }
}
