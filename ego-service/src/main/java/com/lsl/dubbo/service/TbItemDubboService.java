package com.lsl.dubbo.service;

import java.util.List;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.pojo.TbItem;
import com.lsl.pojo.TbItemDesc;
import com.lsl.pojo.TbItemParamItem;

public interface TbItemDubboService {

	/**
	 * 商品分页查询
	 * @param rows 页面显示的数据数
	 * @param page 当前页
	 * @return
	 */
	EasyUIDataGrid show(int rows,int page);
	/**
	 * 通过传过来的数据状态，对数据进行逻辑上的操作（删除，上架，下架）
	 * @param tbItem
	 * @return
	 */
	int updByItem(TbItem tbItem);
	
	/**
	 * 新增商品
	 * @param tbItem
	 * @return
	 */
	int insTbItem(TbItem tbItem);
	
	/**
	 * 新增商品事务回滚
	 * @param tbItem
	 * @param tbItemDesc
	 * @return
	 */
	int insTbItemDesc(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem paramItem) throws Exception;
	
	/**
	 * 通过状态码查询全部商品信息
	 * @param status
	 * @return
	 */
	List<TbItem> selAllByStatus(byte status);
	
	/**
	 * 根据id查询商品
	 * @return
	 */
	TbItem selById(long id);
	
	/**
	 * 根据id更新
	 * @param tbItem
	 * @param tbItemDesc
	 * @param paramItem
	 * @return
	 */
	int updById(TbItem tbItem,TbItemDesc tbItemDesc);
}
