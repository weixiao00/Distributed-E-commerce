package com.lsl.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbContentDubboService;
import com.lsl.pojo.TbContent;
import com.lsl.portal.service.TbContentService;

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
	public String selBigPic() {
		//先去redis里查看，如果存在，取出来
		if (jedisDao.exists(key)){
			String value = jedisDao.get(key);
			if(value != null && !value.equals("")){
				System.out.println("redis run Big");
				return value;
			}
		}
		
		//查询数据库，然后存到redis里
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
		return dbValue;
	}

}
