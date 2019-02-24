package com.lsl.dubbo.service;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.pojo.TbItemParam;

public interface TbItemParamDubboService {

	/**
	 * 显示规格参数，
	 * @param page 当前页
	 * @param rows 当前页显示的个数
	 * @return
	 */
	EasyUIDataGrid showPageParam(int page,int rows);
	
	/**
	 * 删除规格参数
	 * @param ids 规格参数的id，以,隔开
	 * @return
	 */
	int delByIds(String ids) throws Exception;
	
	/**
	 * 查询商品类目（规格参数），是否存在
	 * @param catId
	 * @return
	 */
	TbItemParam selByCatId(long catId);
	
	/**
	 * 插入商品类目（规格参数）
	 * @param itemParam 商品类目对象
	 * @return 返回 1（成功）,0（失败）
	 */
	int insTbItemParam(TbItemParam itemParam);
}
