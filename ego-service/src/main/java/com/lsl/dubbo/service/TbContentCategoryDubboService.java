package com.lsl.dubbo.service;

import java.util.List;

import com.lsl.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {

	/**
	 * 根据父类目查询子类目
	 * @param pid
	 * @return
	 */
	List<TbContentCategory> selByPid(long pid);
	
	/**
	 * 新增类目
	 * @param category
	 * @return
	 */
	int insContentCategory(TbContentCategory category);
	
	/**
	 * 修改父类目的IsParent属性，根据父类目id
	 * @param parentId
	 * @param isParent
	 * @return
	 */
	int updIsParentById(TbContentCategory category);
	/**
	 * 根据id查询父类目id
	 * @param id
	 * @return
	 */
	TbContentCategory selParentById(long id);
}
