package com.lsl.manage.service;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.pojo.TbItem;
import com.lsl.pojo.TbItemDesc;
import com.lsl.pojo.TbItemParamItem;

public interface TbItemService {

	/**
	 * 下属多有数据
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(int page, int rows);
	
	/**
	 * 根据数据的状态进行删除，上架，下架
	 * @param ids
	 * @param status
	 * @return
	 */
	int updByItem(String ids,byte status);
	
	/**
	 * 插入数据
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 * @throws Exception
	 */
	int insert(TbItem item,String desc,String itemParams) throws Exception;
	
	/**
	 * 根据id更新
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 */
	int updById(TbItem tbItem,TbItemDesc tbItemDesc);
}
