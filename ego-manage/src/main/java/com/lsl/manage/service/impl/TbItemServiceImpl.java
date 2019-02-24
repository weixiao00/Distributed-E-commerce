package com.lsl.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.commons.util.HttpClientUtil;
import com.lsl.commons.util.IDUtils;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbItemDescDubboService;
import com.lsl.dubbo.service.TbItemDubboService;
import com.lsl.manage.service.TbItemService;
import com.lsl.pojo.TbItem;
import com.lsl.pojo.TbItemDesc;
import com.lsl.pojo.TbItemParamItem;

import ego.lsl.redis.dao.JedisDao;
@Service
public class TbItemServiceImpl implements TbItemService {

	@Value("${httpclient.url.add}")
	private String url;
	@Value("${httpclient.url.delete}")
	private String urlDelete;
	@Value("${redis.item.key}")
	private String itemKey;
	@Autowired
	private JedisDao jedisDao;
	@Reference
	private TbItemDubboService tbItemDubboService;
	
	@Reference
	private TbItemDescDubboService tbItemDescDubboService;
	/**
	 * 显示所有商品
	 */
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		return tbItemDubboService.show(rows, page);
	}
	/**
	 * 逻辑上的商品 1:上架,2:下架,3:被删除
	 */
	@Override
	public int updByItem(String ids,byte status) {
		int index = 0;
		String[] idArray = ids.split(",");
		TbItem tbItem = new TbItem();
		for(String id : idArray){
			tbItem.setId(Long.parseLong(id));
			tbItem.setStatus(status);
			index += tbItemDubboService.updByItem(tbItem);
		}
		if(index == idArray.length){
			if (status == 2 || status == 3) {
				//下架和删除同步到solr和redis
				for(String id : idArray){
					//同步solr数据
					new Thread() {
						public void run() {
							HttpClientUtil.doPostJson(urlDelete, id);
						}
					}.start();
					//同步redis数据
					String key = itemKey + id;
					jedisDao.del(key);
				}
			}
			if (status == 1) {
				//上架同步到solr
				for(String id : idArray){
					new Thread() {
						public void run() {
							TbItem item = tbItemDubboService.selById(Long.parseLong(id));
							HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(item));
						}
					}.start();
				}
			}
 			return 1;
		}else{
			return 0;
		}
	}
	@Override
	
	public int insert(TbItem item, String desc,String itemParams) throws Exception {
		
		//不考虑事务回滚功能
//		long id = IDUtils.genItemId();
//		item.setId(id);
//		Date date = new Date();
//		System.out.println(date);
//		item.setCreated(date);
//		item.setUpdated(date);
//		item.setStatus((byte)1);
//		int index = tbItemDubboService.insTbItem(item);
//		if(index > 0){
//			TbItemDesc tbItemDesc = new TbItemDesc();
//			tbItemDesc.setItemId(id);
//			tbItemDesc.setCreated(date);
//			tbItemDesc.setUpdated(date);
//			tbItemDesc.setItemDesc(desc);
//			index += tbItemDescDubboService.insTbItemDesc(tbItemDesc);
//		}
//		if(index == 2){
//			return 1;
//		}
//		return 0;
		//考虑事务回滚功能
		long id = IDUtils.genItemId();
		item.setId(id);
		Date date = new Date();
		System.out.println(date);
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte)1);
		int index = 0;
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		tbItemDesc.setItemDesc(desc);
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setCreated(date);
		paramItem.setUpdated(date);
		paramItem.setItemId(id);
		paramItem.setParamData(itemParams);
		index = tbItemDubboService.insTbItemDesc(item, tbItemDesc,paramItem);
		//发送数据到另一个tomcat的控制器，模拟浏览器的功能，如何还需要返回数据的话，那会使用jsonp跨区请求数据（htpclient也可以，但是我不会）
		//同步solr
		new Thread() {
			public void run(){
//				Map<String,Object> map = new HashMap<>();
//				map.put("item",item);
//				map.put("desc",desc);
				HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(item));
				
			}
		}.start();
		return index;
	}
	@Override
	public int updById(TbItem tbItem,TbItemDesc tbItemDesc) {
		int index = tbItemDubboService.updById(tbItem, tbItemDesc);
		if (index == 1) {
			//同步redis
			String key = itemKey + tbItem.getId();
			if(jedisDao.exists(key)){
				jedisDao.del(key);
			}
			//同步solr
			HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(tbItem));
			System.out.println("同步完成");
		}
		return index; 
	}

}
