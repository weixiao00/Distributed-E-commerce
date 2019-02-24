package com.lsl.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.pojo.TbItemChild;

public interface CartService {

	/**
	 * 将选中的商品插入到购物车，实际上是放到redis里
	 */
	void insCartInfo(Long id,int num,HttpServletRequest request);
	
	/**
	 * 显示购物车商品
	 * @return
	 */
	List<TbItemChild> showCartInfo(HttpServletRequest request);
	
	/**
	 * 根据id修改商品数量
	 * @param id
	 * @param num
	 * @return
	 */
	EgoResult updNum(Long id,int num,HttpServletRequest request);
	
	/**
	 * 根据id删除商品
	 * @param id
	 * @param requset
	 * @return
	 */
	EgoResult deleteCartItem(Long id,HttpServletRequest requset);
}
