package com.lsl.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.cart.service.CartService;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.pojo.TbItemChild;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	@RequestMapping("/cart/add/{id}.html")
	public String showCartSuccess(@PathVariable(value="id") Long id,int num,HttpServletRequest request) {
		cartService.insCartInfo(id, num, request);
		return "cartSuccess";
	}
	
	@RequestMapping("cart/cart.html")
	public String showCartInfo(HttpServletRequest request,ModelMap map) {
		List<TbItemChild> list = cartService.showCartInfo(request);
		map.addAttribute("cartList",list);
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{id}/{num}.action")
	@ResponseBody
	public EgoResult updNum(@PathVariable Long id,@PathVariable int num,HttpServletRequest request) {
		return cartService.updNum(id, num, request);
	}
	
	@RequestMapping("cart/delete/{id}.action")
	@ResponseBody
	public EgoResult deleteCartItem(@PathVariable(value="id") Long id,HttpServletRequest request) {
		return cartService.deleteCartItem(id, request);
	}
}
