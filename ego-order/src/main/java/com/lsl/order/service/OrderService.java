package com.lsl.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.pojo.TbItemChild;
import com.lsl.order.pojo.MyOrderParam;

public interface OrderService {

	/**
	 * 显示订单信息
	 * @param list
	 * @param request
	 * @return
	 */
	List<TbItemChild> showOrderInfo(List<Long> list,HttpServletRequest request);
	
	/**
	 * 创建用户的订单信息
	 * @param param
	 * @return
	 */
	EgoResult creatOrderInfo(MyOrderParam param,HttpServletRequest request);
}
