package com.lsl.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.TbItemChild;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbItemDubboService;
import com.lsl.item.service.TbItemService;
import com.lsl.pojo.TbItem;

import ego.lsl.redis.dao.JedisDao;

@Service
public class TbItemServiceImpl implements TbItemService {

	@Value("${redis.item.key}")
	private String itemKey;
	@Autowired
	private JedisDao jedisDao;
	@Reference
	private TbItemDubboService tbItemDubboService;
	@Override
	public TbItemChild show(long id) {
		//先去redis里去取商品，如果有则取出来，返回。否则去mysql里查，然后放到redis里
		String key = itemKey + id;
		if (jedisDao.exists(key)){
			String item = jedisDao.get(key);
			if (item != null && !item.equals("")){
				System.out.println("redis item");
				return JsonUtils.jsonToPojo(item, TbItemChild.class);
			}
		}
		TbItem item = tbItemDubboService.selById(id);
		TbItemChild child = new TbItemChild();
		child.setId(item.getId());
		child.setPrice(item.getPrice());
		child.setSellPoint(item.getSellPoint());
		child.setTitle(item.getTitle());
		child.setImages(item.getImage()==null || item.getImage().equals("")?new String[1] : item.getImage().split(","));
		//放到redis里
		jedisDao.set(key, JsonUtils.objectToJson(child));
		return child;
	}

}
