package com.lsl.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.manage.service.TbContentService;
import com.lsl.pojo.TbContent;

@Controller
public class TbContentController {

	@Autowired
	private TbContentService tbContentService;
	/**
	 * 分页查询内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGrid selContentByPage(long categoryId,int page,int rows){
		return tbContentService.selByPage(categoryId, page, rows); 
	}
	
	/**
	 * 新增内容
	 * @param tbContent
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public EgoResult insTbContent(TbContent tbContent){
		return tbContentService.insTbContent(tbContent);
	}
	/**
	 * 更新内容
	 * @param tbContent
	 * @return
	 */
	@RequestMapping("rest/content/edit")
	@ResponseBody
	public EgoResult updTbContent(TbContent tbContent){
		return tbContentService.updTbContent(tbContent);
	}
	
	/**
	 * 删除内容
	 * @param ids
	 * @return
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public EgoResult delTbContent(String ids){
		return tbContentService.delTbContent(ids);
	}
}
