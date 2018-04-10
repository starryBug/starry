package com.starry.service;

import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
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
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JestService {

	/**
	 * 获取JestClient对象
	 * @return
	 */
	public JestClient getJestClient() {  
	    JestClientFactory factory = new JestClientFactory();
	    factory.setHttpClientConfig(new HttpClientConfig
	        .Builder("http://localhost:9200")
	        .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create())
	        .connTimeout(1500)
	        .readTimeout(3000)
	        .multiThreaded(true)
	        .build());
	    return factory.getObject();
	}
	
	/**
	 * 创建索引
	 * @param jestClient
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean createIndex(JestClient jestClient, String indexName) throws Exception {
	    JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
	    return jr.isSucceeded();
	}

	/**
	 * 创建索引
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean createIndex(String indexName) throws Exception {
		JestClient jestClient = getJestClient();
		JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
		closeJestClient(jestClient);
		return jr.isSucceeded();
	}
	
	/**
	 * Put映射
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public boolean createIndexMapping(JestClient jestClient, String indexName, String typeName, String source) throws Exception {
	    PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
	    JestResult jr = jestClient.execute(putMapping);
	    return jr.isSucceeded();
	}

	/**
	 * Put映射
	 * @param indexName
	 * @param typeName
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public boolean createIndexMapping(String indexName, String typeName, String source) throws Exception {
		JestClient jestClient = getJestClient();
		PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
		JestResult jr = jestClient.execute(putMapping);
		closeJestClient(jestClient);
		return jr.isSucceeded();
	}
	/**
	 * Get映射
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @return
	 * @throws Exception
	 */
	public String getIndexMapping(JestClient jestClient, String indexName, String typeName) throws Exception {
	    GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
	    JestResult jr = jestClient.execute(getMapping);
	    return jr.getJsonString();
	}

	/**
	 * Get映射
	 * @param indexName
	 * @param typeName
	 * @return
	 * @throws Exception
	 */
	public String getIndexMapping(String indexName, String typeName) throws Exception {
		JestClient jestClient = getJestClient();
		GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
		JestResult jr = jestClient.execute(getMapping);
		closeJestClient(jestClient);
		return jr.getJsonString();
	}
	
	/**
	 * 索引文档
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public boolean index(JestClient jestClient, String indexName, String typeName, List<Object> objs) throws Exception {
	    Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
	    for (Object obj : objs) {
	    	Index index = new Index.Builder(obj).build();
	    	bulk.addAction(index);
	    }
	    BulkResult br = jestClient.execute(bulk.build());
	    return br.isSucceeded();
	}

	/**
	 * 索引文档
	 * @param indexName
	 * @param typeName
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public boolean index(String indexName, String typeName, List<Object> objs) throws Exception {
		JestClient jestClient = getJestClient();
		Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
		for (Object obj : objs) {
			Index index = new Index.Builder(obj).build();
			bulk.addAction(index);
		}
		BulkResult br = jestClient.execute(bulk.build());
		closeJestClient(jestClient);
		return br.isSucceeded();
	}
	
	/**
	 * 搜索文档
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public SearchResult search(JestClient jestClient, String indexName, String typeName, String query) throws Exception {
	    Search search = new Search.Builder(query)
	    	.addIndex(indexName)
	    	.addType(typeName)
	        .build();
	    return jestClient.execute(search);
	}

	/**
	 * 搜索文档
	 * @param indexName
	 * @param typeName
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public SearchResult search(String indexName, String typeName, String query) throws Exception {
		JestClient jestClient = getJestClient();
		Search search = new Search.Builder(query)
				.addIndex(indexName)
				.addType(typeName)
				.build();
		SearchResult searchResult = jestClient.execute(search);
		closeJestClient(jestClient);
		return searchResult;
	}
	
	/**
	 * Count文档
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Double count(JestClient jestClient, String indexName, String typeName, String query) throws Exception {
	    Count count = new Count.Builder()
	    	.addIndex(indexName)
	        .addType(typeName)
	        .query(query)
	        .build();
	    CountResult results = jestClient.execute(count); 
	    return results.getCount();
	}

	/**
	 * Count文档
	 * @param indexName
	 * @param typeName
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Double count(String indexName, String typeName, String query) throws Exception {
		JestClient jestClient = getJestClient();
		Count count = new Count.Builder()
				.addIndex(indexName)
				.addType(typeName)
				.query(query)
				.build();
		CountResult results = jestClient.execute(count);
		closeJestClient(jestClient);
		return results.getCount();
	}
	
	/**
	 * Get文档
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JestResult get(JestClient jestClient, String indexName, String typeName, String id) throws Exception {
	    Get get = new Get.Builder(indexName, id).type(typeName).build();
	    return jestClient.execute(get);
	}

	/**
	 * Get文档
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JestResult get(String indexName, String typeName, String id) throws Exception {
		JestClient jestClient = getJestClient();
		Get get = new Get.Builder(indexName, id).type(typeName).build();
		JestResult jestResult = jestClient.execute(get);
		closeJestClient(jestClient);
		return jestResult;
	}
	
	/**
	 * Delete索引
	 * @param jestClient
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean delete(JestClient jestClient, String indexName) throws Exception {
	    JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
	    return jr.isSucceeded();
	}

	/**
	 * Delete索引
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String indexName) throws Exception {
		JestClient jestClient = getJestClient();
		JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
		closeJestClient(jestClient);
		return jr.isSucceeded();
	}
	
	/**
	 * Delete文档
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(JestClient jestClient, String indexName, String typeName, String id) throws Exception {
	    DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
	    return dr.isSucceeded();
	}

	/**
	 * Delete文档
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String indexName, String typeName, String id) throws Exception {
		JestClient jestClient = getJestClient();
		DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
		closeJestClient(jestClient);
		return dr.isSucceeded();
	}
	
	/**
	 * 关闭JestClient客户端
	 * @param jestClient
	 * @throws Exception
	 */
	public void closeJestClient(JestClient jestClient) throws Exception {
	    if (jestClient != null) {
	    	jestClient.shutdownClient();
	    }
	}

}
