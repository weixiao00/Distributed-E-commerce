package com.lsl.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbContentDubboService;
import com.lsl.manage.service.TbContentService;
import com.lsl.pojo.TbContent;

import ego.lsl.redis.dao.JedisDao;
@Service
public class TbContentServiceImpl implements TbContentService {

	@Value("${redis.bigPic}")
	private String key;
	@Autowired
	private JedisDao jedisDao;
	@Reference
	private TbContentDubboService tbContentDubboService;
	@Override
	public EasyUIDataGrid selByPage(long categoryId, int page, int rows) {
		return tbContentDubboService.selContentbyPage(categoryId, page, rows);
	}
	@Override
	public EgoResult insTbContent(TbContent tbContent) {
		Date date = new Date();
		tbContent.setUpdated(date);
		tbContent.setCreated(date);
		int index = tbContentDubboService.insTbContent(tbContent);
		EgoResult er = new EgoResult();
		if(index > 0){
			if(jedisDao.exists(key)){
				String value = jedisDao.get(key);
				if(value != null && !value.equals("")){
					List<HashMap> list = JsonUtils.jsonToList(value, HashMap.class);
					HashMap<String,Object> map = new HashMap<>();
					map.put("srcB",tbContent.getPic2());
					map.put("height",240);
					map.put("alt","对不起，图片没有加载成功");
					map.put("width",670);
					map.put("src",tbContent.getPic());
					map.put("widthB",550);
					map.put("href",tbContent.getUrl());
					map.put("heightB",240);
					
					if(list.size() == 6) {
						list.remove(5);
					}
					list.add(0, map);
					jedisDao.set(key, JsonUtils.objectToJson(list));
				}
			}
			er.setStatus(200);
		}
		return er;
	}
	@Override
	public EgoResult updTbContent(TbContent tbContent) {
		EgoResult er = new EgoResult();
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		int index = tbContentDubboService.updTbContent(tbContent);
		if(index > 0) {
			if(jedisDao.exists(key)){
				String value = jedisDao.get(key);
				if(value != null && !value.equals("")){
					//重新查询mysql，存到redis里
					List<TbContent> list = tbContentDubboService.selByCount(6, true);
					List<Map<String,Object>> listMap = new ArrayList<>(); 
					for(TbContent tc : list){
						Map<String,Object> map = new HashMap<>();
						map.put("srcB",tc.getPic2());
						map.put("height",240);
						map.put("alt","对不起，图片没有加载成功");
						map.put("width",670);
						map.put("src",tc.getPic());
						map.put("widthB",550);
						map.put("href",tc.getUrl());
						map.put("heightB",240);
						listMap.add(map);
					}
					String dbValue = JsonUtils.objectToJson(listMap);
					//存储到redis里
					jedisDao.set(key, dbValue);
				}
			}
			er.setStatus(200);
		}
		return er;
	}
	@Override
	public EgoResult delTbContent(String ids) {
		int index = 0;
		EgoResult er = new EgoResult();
		String[] idlist = ids.split(",");
		for (String id : idlist){
			 index = tbContentDubboService.delTbContent(Long.parseLong(id));
		}
		if(index > 0){
			if(jedisDao.exists(key)){
				String value = jedisDao.get(key);
				if(value != null && !value.equals("")){
					//重新查询mysql，存到redis里
					List<TbContent> list = tbContentDubboService.selByCount(6, true);
					List<Map<String,Object>> listMap = new ArrayList<>(); 
					for(TbContent tc : list){
						Map<String,Object> map = new HashMap<>();
						map.put("srcB",tc.getPic2());
						map.put("height",240);
						map.put("alt","对不起，图片没有加载成功");
						map.put("width",670);
						map.put("src",tc.getPic());
						map.put("widthB",550);
						map.put("href",tc.getUrl());
						map.put("heightB",240);
						listMap.add(map);
					}
					String dbValue = JsonUtils.objectToJson(listMap);
					//存储到redis里
					System.out.println("redis restart");
					jedisDao.set(key, dbValue);
				}
			}
			er.setStatus(200);
		}
		return er;
	}

}
