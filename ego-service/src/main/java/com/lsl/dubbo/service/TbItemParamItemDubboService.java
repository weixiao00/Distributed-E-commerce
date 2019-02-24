package com.lsl.dubbo.service;

import com.lsl.pojo.TbItemParamItem;

public interface TbItemParamItemDubboService {

	/**
	 * 显示商品规格参数
	 * @param itemId
	 * @return
	 */
	TbItemParamItem showItemParam(Long itemId);
}
