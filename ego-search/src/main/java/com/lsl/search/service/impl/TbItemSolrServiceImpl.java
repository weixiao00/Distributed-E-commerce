package com.lsl.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.TbItemChild;
import com.lsl.dubbo.service.TbItemCatDubboService;
import com.lsl.dubbo.service.TbItemDescDubboService;
import com.lsl.dubbo.service.TbItemDubboService;
import com.lsl.pojo.TbItem;
import com.lsl.pojo.TbItemCat;
import com.lsl.pojo.TbItemDesc;
import com.lsl.search.service.TbItemSolrService;

@Service
public class TbItemSolrServiceImpl implements TbItemSolrService {

	@Reference
	private TbItemDubboService tbItemDubboService;
	@Reference
	private TbItemCatDubboService tbItemCatDubboService;
	@Reference
	private TbItemDescDubboService tbItemDescDubboService;
	
	@Autowired
	private CloudSolrClient client;
	@Override
	public void init() throws SolrServerException, IOException {
		List<TbItem> list = tbItemDubboService.selAllByStatus((byte)1);
		List<SolrInputDocument> listResult = new ArrayList<>();
		for(TbItem item : list) {
			//查询商品类目
			TbItemCat itemCat = tbItemCatDubboService.dataCat(item.getCid());
			//查询商品描述
			TbItemDesc itemDesc = tbItemDescDubboService.selDescById(item.getId());
//			System.out.println(itemDesc);
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSellPoint());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_image", item.getImage());
			doc.addField("item_category_name", itemCat.getName());
			if (itemDesc != null) {
			doc.addField("item_desc", itemDesc.getItemDesc());
			} else{
				doc.addField("item_desc", "");
			}
			client.add(doc);
//			listResult.add(doc);
		}
//		client.add(listResult);
		client.commit();
	}
	@Override
	public Map<String, Object> selBySolr(String query,int page,int rows) throws SolrServerException, IOException {
		SolrQuery params = new SolrQuery();
		params.setStart((page-1)*rows);
		params.setRows(rows);
		//设置q,条件
		params.setQuery("item_keywords:"+query);
		//设置高亮
		params.setHighlight(true);
		params.addHighlightField("item_title");
		params.setHighlightSimplePre("<span style='color:red'>");
		params.setHighlightSimplePost("</span>");
		QueryResponse response = client.query(params);
		SolrDocumentList solrDocumentList = response.getResults();
		Map<String, Map<String, List<String>>> hl = response.getHighlighting();
		List<TbItemChild> listResult = new ArrayList<>();
		for (SolrDocument doc : solrDocumentList) {
			TbItemChild child = new TbItemChild();
			child.setId(Long.parseLong(doc.getFieldValue("id").toString()));
			child.setPrice((Long)doc.getFieldValue("item_price"));
			child.setSellPoint(doc.getFieldValue("item_sell_point")==null?"":doc.getFieldValue("item_sell_point").toString());
			Object image = doc.getFieldValue("item_image");
			child.setImages(image==null||image.equals("")?new String[1]:image.toString().split(","));
			List<String> list = hl.get(doc.getFieldValue("id")).get("item_title");
			if (list != null && list.size() > 0){
				child.setTitle(list.get(0));
			} else {
				child.setTitle(doc.getFieldValue("item_title")==null?"":doc.getFieldValue("item_title").toString());
			}
			listResult.add(child);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("itemList", listResult);
		map.put("totalPages", solrDocumentList.getNumFound()%rows==0?solrDocumentList.getNumFound()/rows:solrDocumentList.getNumFound()/rows+1);
		return map;
	}
	@Override
	public int addItem(TbItem item) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", item.getId());
		doc.addField("item_title", item.getTitle());
		doc.addField("item_sell_point", item.getSellPoint());
		doc.addField("item_price", item.getPrice());
		doc.addField("item_image", item.getImage());
		doc.addField("item_category_name",tbItemCatDubboService.dataCat(item.getCid()).getName());
		doc.addField("item_desc", tbItemDescDubboService.selDescById(item.getId()));
		UpdateResponse response = client.add(doc);
		client.commit();
		if (response.getStatus() == 0) {
			return 1;
		}
		return 0;
	}
	@Override
	public int deleteSolrItem(String id) throws SolrServerException, IOException {
		System.out.println(id);
		UpdateResponse response = client.deleteById(id);
		client.commit();
		if (response.getStatus() == 0){
			System.out.println("同步solr成功");
			return 1;
		}
		else{
			return 0;
		}
	}
}
