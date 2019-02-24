package com.lsl.manage.service;

import java.util.List;

import com.lsl.commons.pojo.EasyUiTree;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.pojo.TbContentCategory;

public interface TbContentCategoryService {

	/**
	 * 查询内容类目，分层查询，一次查一层，返回固定的格式结果
	 * @param id
	 * @return
	 */
	List<EasyUiTree> showCategory(long id);
	
	/**
	 * 新增类目
	 * @param category
	 * @return
	 */
	EgoResult create(TbContentCategory category);
	/**
	 * 重命名
	 * @param category
	 * @return
	 */
	EgoResult update(TbContentCategory category);
	
	/**
	 * 删除类目
	 * @param category
	 * @return
	 */
	EgoResult delete(TbContentCategory category);
}
