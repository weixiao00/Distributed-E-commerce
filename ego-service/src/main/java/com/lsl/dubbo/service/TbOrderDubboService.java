package com.lsl.dubbo.service;

import java.util.List;

import com.lsl.commons.pojo.EgoResult;
import com.lsl.pojo.TbOrder;
import com.lsl.pojo.TbOrderItem;
import com.lsl.pojo.TbOrderShipping;

public interface TbOrderDubboService {

	/**
	 * 插入订单信息，包含事务回滚
	 * @param order
	 * @param list
	 * @param shpping
	 * @return
	 */
	int insOrderInfo(TbOrder order,List<TbOrderItem> list,TbOrderShipping shpping);
}
