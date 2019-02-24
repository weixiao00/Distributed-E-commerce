package com.lsl.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.dubbo.service.TbItemDescDubboService;
import com.lsl.item.service.TbItemDescService;
import com.lsl.pojo.TbItemDesc;

import ego.lsl.redis.dao.JedisDao;

@Service
public class TbItemDescServiceImpl implements TbItemDescService {

	@Autowired
	private JedisDao jedisDao;
	@Value("${redis.item.desc.key}")
	private String descKey;
	@Reference
	private TbItemDescDubboService tbItemDescDubboService;
	@Override
	public String showDesc(Long itemId) {
		//先去redis里取
		String key = descKey + itemId;
		if (jedisDao.exists(key)){
			String itemDescValue = jedisDao.get(key);
			 if(itemDescValue != null && ! itemDescValue.equals("")){
				 return itemDescValue;
			 }
		}
		TbItemDesc desc = tbItemDescDubboService.selDescById(itemId);
		if (desc != null){
			//放到redis里
			jedisDao.set(key, desc.getItemDesc());
			return desc.getItemDesc();
		} else{
			return "本商品没有描述";
		}
	}

}
