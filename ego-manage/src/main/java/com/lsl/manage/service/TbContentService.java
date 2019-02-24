package com.lsl.manage.service;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.pojo.TbContent;

public interface TbContentService {

	/**
	 * 分页查询内容
	 * @param category
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selByPage(long categoryId,int page,int rows);
	
	/**
	 * 新增内容
	 * @param tbContent
	 * @return
	 */
	EgoResult insTbContent(TbContent tbContent);
	
	/**
	 * 修改内容
	 * @param tbContent
	 * @return
	 */
	EgoResult updTbContent(TbContent tbContent);
	
	/**
	 * 删除内容
	 * @param tbCOntent
	 * @return
	 */
	EgoResult delTbContent(String ids);
}
