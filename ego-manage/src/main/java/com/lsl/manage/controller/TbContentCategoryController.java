package com.lsl.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.commons.pojo.EasyUiTree;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.manage.service.TbContentCategoryService;
import com.lsl.pojo.TbContentCategory;

@Controller
public class TbContentCategoryController {

	@Autowired
	private TbContentCategoryService tbContentCategoryService;
	/**
	 * 查询商品类目(内容)
	 * @param id
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUiTree> showCategory(@RequestParam(defaultValue="0") long id){
		return tbContentCategoryService.showCategory(id);
	}
	/**
	 * 新增商品类目(内容)
	 * @param category
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public EgoResult create(TbContentCategory category){
		return tbContentCategoryService.create(category);
	}
	/**
	 * 重命名
	 * @param category
	 * @return
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory category){
		return tbContentCategoryService.update(category);
	}
	
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public EgoResult delete(TbContentCategory category){
		return tbContentCategoryService.delete(category);
	}
}
