package com.lsl.dubbo.service;

import java.util.List;

import com.lsl.pojo.TbItemDesc;

public interface TbItemDescDubboService {

	/**
	 * 新增商品描述信息
	 * @param desc
	 * @return
	 */
	int insTbItemDesc(TbItemDesc desc);
	
	/**
	 * 根据商品id查询商品描述
	 * @return
	 */
	TbItemDesc selDescById(long itemId);
}
