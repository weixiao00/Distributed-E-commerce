package com.lsl.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbItemCatDubboService;
import com.lsl.item.pojo.ItemMenu;
import com.lsl.item.pojo.ItemMenuNode;
import com.lsl.item.service.TbItemCatService;
import com.lsl.pojo.TbItemCat;

import ego.lsl.redis.dao.JedisDao;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {

	@Value("${redis.itemCat}")
	private String key;
	@Autowired
	private JedisDao jedisDao;
	@Reference
	private TbItemCatDubboService tbItemCatDubboService;
	@Override
	public ItemMenu showcatMenu() {
		//先去redis里查，如果有转化成pojo返回
		if (jedisDao.exists(key)){
			String value = jedisDao.get(key);
			if (value != null && !value.equals("")){
				//没有做后台更新数据同步到redis里
				 ItemMenu itemMenu = JsonUtils.jsonToPojo(value, ItemMenu.class);
				 System.out.println("redis run itemCat");
				 return itemMenu;
			}
		}
		
		
		//查数据库，放到redis里，返回
		//查询返回所有的一级目录
		List<TbItemCat> list = tbItemCatDubboService.show(0);
		ItemMenu im = new ItemMenu();
		im.setData(selAllMenu(list));
		jedisDao.set(key, JsonUtils.objectToJson(im));
		return im;
	}
	
	public List<Object> selAllMenu(List<TbItemCat> list){
		List<Object> listNode = new ArrayList<>();
		for(TbItemCat tbItemCat : list){
			if(!tbItemCat.getIsParent()){
				listNode.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
				continue;
			}
			ItemMenuNode imn = new ItemMenuNode();
			imn.setU("/products/"+tbItemCat.getId()+".html");
			imn.setN("<a href='/products/1.html'>"+tbItemCat.getName()+"</a>");
			imn.setI(selAllMenu(tbItemCatDubboService.show(tbItemCat.getId())));
			listNode.add(imn);
		}
		return listNode;
	}

}
