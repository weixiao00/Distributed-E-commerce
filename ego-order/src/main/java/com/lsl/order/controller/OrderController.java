package com.lsl.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lsl.commons.pojo.TbItemChild;
import com.lsl.order.pojo.MyOrderParam;
import com.lsl.order.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@RequestMapping("/order/order-cart.html")
	public String showOrderCart(@RequestParam(value="id",defaultValue="") List<Long> ids,HttpServletRequest request,ModelMap map) {
		List<TbItemChild> list = orderService.showOrderInfo(ids, request);
		map.addAttribute("cartList",list);
		return "order-cart";
	}
	
	@RequestMapping("/order/create.html")
	public String insOrderInfo(MyOrderParam orderParam,HttpServletRequest request) {
		orderService.creatOrderInfo(orderParam, request);
		return "my-orders";
	}
}
