package com.lsl.item.service;

import com.lsl.commons.pojo.TbItemChild;
import com.lsl.pojo.TbItem;

public interface TbItemService {

	/**
	 * 根据id查询商品信息，显示具体商品
	 * @param id
	 * @return
	 */
	TbItemChild show(long id);
}
