package com.lsl.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.cart.service.CartService;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.pojo.TbItemChild;
import com.lsl.commons.util.CookieUtils;
import com.lsl.commons.util.HttpClientUtil;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbItemDubboService;
import com.lsl.pojo.TbItem;

import ego.lsl.redis.dao.JedisDao;

@Service
public class CartServiceImpl implements CartService {

	@Value("${redis.item.cart.key}")
	private String cartKey;
	@Value("${httpclient.url.passport}")
	private String tokenUrl;
	@Reference
	private TbItemDubboService tbItemDubboService;
	@Autowired
	private JedisDao jedisDao;
	
	/**
	 * 获取redis的key
	 * @param request
	 * @return
	 */
	private String getRedisKey(HttpServletRequest request) {
		//获取redis的key
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String json = HttpClientUtil.doPost(tokenUrl+cookieValue);
		EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
		LinkedHashMap data = (LinkedHashMap)er.getData();
		String redisKey = cartKey+data.get("username");
		return redisKey;
	}
	@Override
	public void insCartInfo(Long id, int num, HttpServletRequest request) {
		String redisKey = getRedisKey(request);
		List<TbItemChild> list = new ArrayList<>();
		//商品是一个集合，有商品，而且商品集合里有我要存储的商品,就将原数量和新数量相加
		if (jedisDao.exists(redisKey)) {
			String value = jedisDao.get(redisKey);
			if (value != null && !value.equals("")) {
				list = JsonUtils.jsonToList(value, TbItemChild.class);
				for (TbItem tbItem : list) {
					if ((long)tbItem.getId() == id) {
						tbItem.setNum(tbItem.getNum() + num);
						jedisDao.set(redisKey, JsonUtils.objectToJson(list));
						return;
					}
				}
			}
		}
		//redis的集合里没有商品或者集合里没有我要购买的商品
		TbItemChild child = new TbItemChild();
		TbItem item = tbItemDubboService.selById(id);
		child.setId(id);
		child.setNum(num);
		child.setPrice(item.getPrice());
		child.setSellPoint(item.getSellPoint());
		child.setTitle(item.getTitle());
		child.setImages(item.getImage()!= null && !item.getImage().equals("")?item.getImage().split(","):new String[1]);
		list.add(child);
		jedisDao.set(redisKey, JsonUtils.objectToJson(list));
	}
	@Override
	public List<TbItemChild> showCartInfo(HttpServletRequest request) {
		String redisKey = getRedisKey(request);
		String json = jedisDao.get(redisKey);
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		return list;
	}
	@Override
	public EgoResult updNum(Long id, int num, HttpServletRequest request) {
		EgoResult er = new EgoResult();
		String redisKey = getRedisKey(request);
		String json = jedisDao.get(redisKey);
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		for (TbItemChild child : list) {
			if ((long)child.getId() == id) {
				child.setNum(num);
				String ok = jedisDao.set(redisKey, JsonUtils.objectToJson(list));
				if (ok.equals("OK")) {
					return er;
				}
			}
		}
		return null;
	}
	@Override
	public EgoResult deleteCartItem(Long id, HttpServletRequest request) {
		EgoResult er = new EgoResult();
		String redisKey = getRedisKey(request);
		String value = jedisDao.get(redisKey);
		List<TbItemChild> list = JsonUtils.jsonToList(value, TbItemChild.class);
		for (TbItemChild child : list) {
			if ((long)child.getId() == id) {
				list.remove(child);
				if (list.size() != 0) {
					jedisDao.set(redisKey, JsonUtils.objectToJson(list));
				} else {
					jedisDao.del(redisKey);
				}
				er.setStatus(200);
				return er;
			}
		}
		return null;
	}

	
}
