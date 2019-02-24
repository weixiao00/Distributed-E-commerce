package com.lsl.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lsl.commons.pojo.EgoResult;
import com.lsl.dubbo.service.TbOrderDubboService;
import com.lsl.ego.dao.TbOrderItemMapper;
import com.lsl.ego.dao.TbOrderMapper;
import com.lsl.ego.dao.TbOrderShippingMapper;
import com.lsl.pojo.TbOrder;
import com.lsl.pojo.TbOrderItem;
import com.lsl.pojo.TbOrderShipping;

public class TbOrderDubboServiceImpl implements TbOrderDubboService {

	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Override
	@Transactional
	public int insOrderInfo(TbOrder order, List<TbOrderItem> list, TbOrderShipping shpping) {
		int index = 0;
		try {
			index += tbOrderMapper.insertSelective(order);
			for (TbOrderItem orderItem : list) {
				index += tbOrderItemMapper.insertSelective(orderItem);
			}
			index += tbOrderShippingMapper.insertSelective(shpping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (index == 2 + list.size()) {
			return 1;
		} 
		return 0;
	}

}
