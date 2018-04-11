package com.starry.service;

import com.starry.domain.entity.UserFile;
import com.starry.elasticsearch.annotation.JestQueryField;
import com.starry.elasticsearch.domain.BaseElasticSearchEntity;
import com.starry.util.LambdaUtil;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Count;
import io.searchbox.core.CountResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class JestService {

    @Autowired
    private JestClient jestClient;


    /**
     * 创建索引
     *
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean createIndex(String indexName) throws Exception {
        JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
        return jr.isSucceeded();
    }


    /**
     * Put映射
     *
     * @param indexName
     * @param typeName
     * @param source
     * @return
     * @throws Exception
     */
    public boolean createIndexMapping(String indexName, String typeName, String source) throws Exception {
        PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
        JestResult jr = jestClient.execute(putMapping);
        return jr.isSucceeded();
    }

    /**
     * Get映射
     *
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    public String getIndexMapping(String indexName, String typeName) throws Exception {
        GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
        JestResult jr = jestClient.execute(getMapping);
        return jr.getJsonString();
    }

    /**
     * 索引文档
     *
     * @param indexName
     * @param typeName
     * @param objs
     * @return
     * @throws Exception
     */
    public boolean indexList(String indexName, String typeName, List<Object> objs) throws Exception {
        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        for (Object obj : objs) {
            Index index = new Index.Builder(obj).build();
            bulk.addAction(index);
        }
        BulkResult br = jestClient.execute(bulk.build());
        return br.isSucceeded();
    }

    public boolean index(BaseElasticSearchEntity obj) throws Exception {
        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(obj.getElasticIndexName()).defaultType(obj.getElasticTypeName());
        bulk.addAction(new Index.Builder(obj).build());
        BulkResult br = jestClient.execute(bulk.build());
        return br.isSucceeded();
    }


    /**
     * 搜索文档
     *
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    public SearchResult search(String indexName, String typeName, String query) throws Exception {
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .build();
        SearchResult searchResult = jestClient.execute(search);
        return searchResult;
    }
//    public SearchResult search(BaseElasticSearchEntity qryObj) throws Exception {
//        Search search = new Search.Builder(query)
//                .addIndex(indexName)
//                .addType(typeName)
//                .build();
//        SearchResult searchResult = jestClient.execute(search);
//        return searchResult;
//    }


    /**
     * Count文档
     *
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    public Double count(String indexName, String typeName, String query) throws Exception {
        Count count = new Count.Builder()
                .addIndex(indexName)
                .addType(typeName)
                .query(query)
                .build();
        CountResult results = jestClient.execute(count);
        return results.getCount();
    }


    /**
     * Get文档
     *
     * @param indexName
     * @param typeName
     * @param id
     * @return
     * @throws Exception
     */
    public JestResult get(String indexName, String typeName, String id) throws Exception {
        Get get = new Get.Builder(indexName, id).type(typeName).build();
        JestResult jestResult = jestClient.execute(get);
        return jestResult;
    }


    /**
     * Delete索引
     *
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean delete(String indexName) throws Exception {
        JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
        return jr.isSucceeded();
    }


    /**
     * Delete文档
     *
     * @param indexName
     * @param typeName
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(String indexName, String typeName, String id) throws Exception {
        DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
        return dr.isSucceeded();
    }
    /**
     * 条件查询
     *
     * @param qryObj 条件列表
     * @return 结果集
     */
    public List<UserFile> search(UserFile qryObj) {
        try {
            SearchResult result = jestClient.execute(new Search.Builder(buildSearch(qryObj).toString())
                    // multiple index or types can be added.
                    .addIndex(qryObj.getElasticIndexName())
                    .addType(qryObj.getElasticTypeName())
                    .build());
            return result.getSourceAsObjectList(UserFile.class, false);

        } catch (Exception e) {
            throw new RuntimeException("search exception", e);
        }
    }

    private SearchSourceBuilder buildSearch(UserFile qryObj) throws Exception{
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        asList(qryObj.getClass().getDeclaredFields()).stream().forEach(LambdaUtil.wrapException( e -> {
            JestQueryField annotation = e.getAnnotation(JestQueryField.class);
            if (nonNull(annotation)){
                e.setAccessible(true);
                boolQueryBuilder.filter(QueryBuilders.termQuery(e.getName(), buildQueryFieldVal(e.get(qryObj))));
            }
        }));
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;
    }
    private String buildQueryFieldVal(Object object) {
        return object.toString();
    }

}
