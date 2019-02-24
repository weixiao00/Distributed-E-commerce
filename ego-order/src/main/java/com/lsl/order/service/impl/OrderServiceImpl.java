package com.lsl.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.pojo.TbItemChild;
import com.lsl.commons.util.CookieUtils;
import com.lsl.commons.util.HttpClientUtil;
import com.lsl.commons.util.IDUtils;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbItemDubboService;
import com.lsl.dubbo.service.TbOrderDubboService;
import com.lsl.order.pojo.MyOrderParam;
import com.lsl.order.service.OrderService;
import com.lsl.pojo.TbItem;
import com.lsl.pojo.TbOrder;
import com.lsl.pojo.TbOrderItem;
import com.lsl.pojo.TbOrderShipping;

import ego.lsl.redis.dao.JedisDao;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${redis.item.cart.key}")
	private String cartKey;
	@Value("${httpclient.url.passport}")
	private String tokenUrl;
	@Autowired
	private JedisDao jedisDao;
	@Reference
	private TbItemDubboService tbItemDubboService;
	@Reference
	private TbOrderDubboService tbOrderDubboService;
	@Override
	public List<TbItemChild> showOrderInfo(List<Long> list, HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String json = HttpClientUtil.doPost(tokenUrl+cookieValue);
		EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
		LinkedHashMap data = (LinkedHashMap)er.getData();
		String redisKey = cartKey+data.get("username");
		String value = jedisDao.get(redisKey);
		List<TbItemChild> listResult = JsonUtils.jsonToList(value, TbItemChild.class);
		List<TbItemChild> listNew = new ArrayList<>();
		for (TbItemChild tbItemChild : listResult) {
			for (Long id : list) {
				if ((long)id == (long)tbItemChild.getId()) {
					TbItem item = tbItemDubboService.selById(id);
					if (item.getNum() >= tbItemChild.getNum()) {
						tbItemChild.setEnough(true);
					} else {
						tbItemChild.setEnough(false);
					}
					listNew.add(tbItemChild);
				}
			}
		}
		return listNew;
	}
	@Override
	public EgoResult creatOrderInfo(MyOrderParam param,HttpServletRequest request) {
		EgoResult er1 = new EgoResult();
		//创建订单表
		TbOrder order = new TbOrder();
		long orderId = IDUtils.genItemId();
		order.setOrderId(orderId+"");
		order.setPayment(param.getPayment());
		order.setPaymentType(param.getPaymentType());
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String json = HttpClientUtil.doPost(tokenUrl+cookieValue);
		EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
		LinkedHashMap data = (LinkedHashMap)er.getData();
		order.setBuyerNick(data.get("username").toString());
		//创建订单-商品表
		for (TbOrderItem orderItem : param.getOrderItems()) {
			orderItem.setId(IDUtils.genItemId()+"");
			orderItem.setOrderId((Long)orderId+"");
		}
		//创建收货人信息表
		TbOrderShipping orderShipping = param.getOrderShipping();
		orderShipping.setOrderId((Long)orderId+"");
		Date date1 = new Date();
		orderShipping.setUpdated(date1);
		orderShipping.setCreated(date1);
		int index = tbOrderDubboService.insOrderInfo(order, param.getOrderItems(), orderShipping);
		if (index == 1) {
			er1.setStatus(200);
			//删除redis的购物车
			String key = cartKey + data.get("username");
			String value = jedisDao.get(key);
			List<TbItemChild> list = JsonUtils.jsonToList(value, TbItemChild.class);
			Iterator<TbItemChild> iterator = list.iterator();
			while(iterator.hasNext()) {
				TbItemChild child = iterator.next();
				for (TbOrderItem item : param.getOrderItems()) {
					if (Long.parseLong(item.getItemId()) == (long)child.getId()) {
						iterator.remove();
						break;
					}
				}
			}
			jedisDao.set(key, JsonUtils.objectToJson(list));
			return er1;
		}
		return er1;
	}

}
