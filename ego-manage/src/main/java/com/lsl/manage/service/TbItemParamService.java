package com.lsl.manage.service;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.pojo.TbItemParam;

public interface TbItemParamService {

	/**
	 * 分页显示商品规格参数
	 * @param rows 当前页显示数
	 * @param page 当前页
	 * @return
	 */
	EasyUIDataGrid showPagePram(int rows,int page);
	
	/**
	 * 批量删除商品规格参数
	 * @param ids 
	 * @return 
	 * @throws Exception
	 */
	int delete(String ids) throws Exception;
	
	/**
	 * 根据id查询规格参数
	 * @param catId
	 * @return
	 */
	EgoResult showParam(long catId);
	
	/**
	 * 添加类目
	 * @param param 类目对象
	 * @return
	 */
	EgoResult save(TbItemParam param);
}
