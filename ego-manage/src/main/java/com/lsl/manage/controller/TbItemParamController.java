package com.lsl.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.manage.service.TbItemParamService;
import com.lsl.pojo.TbItemParam;

@Controller
public class TbItemParamController {

	@Autowired
	private TbItemParamService tbItemParamService;
	/**
	 * 显示商品规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGrid showPageParam(int page,int rows){
		return tbItemParamService.showPagePram(rows, page);
	}
	/**
	 * 批量删除商品参数
	 * @param ids
	 * @return
	 */
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult er = new EgoResult();
		try {
			int index = tbItemParamService.delete(ids);
			if(index == 1){
				er.setStatus(200);
				return er;
			}
		} catch (Exception e) {
			er.setData(e.getMessage());
			e.printStackTrace();
		}
		return er;
	}
	
	/**
	 * 根据id，显示商品类目 是否存在
	 * @param catId
	 * @return
	 */
	@RequestMapping("/item/param/query/itemcatid/{catId}")
	@ResponseBody
	public EgoResult showParam(@PathVariable long catId){
		return tbItemParamService.showParam(catId);
	}
	/**
	 * 商品类目新增
	 * @param param 商品的paramData自动装配
	 * @param catId 类目Id
	 * @return
	 */
	@RequestMapping("/item/param/save/{catId}")
	@ResponseBody
	public EgoResult save(TbItemParam param,@PathVariable long catId){
		param.setItemCatId(catId);
		return tbItemParamService.save(param);
	}
}
