package com.lsl.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.lsl.pojo.TbItem;

public interface TbItemSolrService {

	/**
	 * 初始化Solr数据
	 */
	void init() throws SolrServerException, IOException;
	
	/**
	 * 根据条件查询solr，分页显示给页面
	 * @return
	 */
	Map<String,Object> selBySolr(String query,int page,int rows) throws SolrServerException, IOException;
	
	/**
	 * 向solr里新增
	 * @param tbItem
	 * @return 返回0 成功，其他失败
	 */
	int addItem(TbItem item) throws SolrServerException, IOException;
	
	/**
	 * 根据id从solr里删除商品
	 * @param id
	 * @return
	 */
	int deleteSolrItem(String id) throws SolrServerException, IOException;
}
