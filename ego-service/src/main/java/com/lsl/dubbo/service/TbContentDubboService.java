package com.lsl.dubbo.service;

import java.util.List;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.pojo.TbContent;

public interface TbContentDubboService {

	/**
	 * 分页显示内容
	 * @param category
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selContentbyPage(long categoryId,int page,int rows);
	
	/**
	 * 新增内容
	 * @param tbContent
	 * @return
	 */
	int insTbContent(TbContent tbContent);
	
	/**
	 * 修改内容
	 * @param tbContent
	 * @return
	 */
	int updTbContent(TbContent tbContent);
	
	/**
	 * 删除内容
	 * @param tbContent
	 * @return
	 */
	int delTbContent(Long id);
	
	/**
	 * 查询固定数目的内容
	 * @param count
	 * @param isSort
	 * @return
	 */
	List<TbContent> selByCount(int count,boolean isSort);
}
