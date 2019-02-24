package com.lsl.manage.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.manage.service.TbItemService;
import com.lsl.pojo.TbItem;
import com.lsl.pojo.TbItemDesc;
import com.lsl.pojo.TbItemParamItem;

@Controller
public class TbItemController {

	@Autowired
	private TbItemService tbItemService;
	
	/**
	 * 显示商品
	 * @param page 当前页
	 * @param rows 本页记录数
	 * @return
	 */
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid show(int page ,int rows){
		return tbItemService.show(page, rows);
	}
	
	/**
	 * 显示商品修改页面
	 * @return
	 */
	@RequestMapping("/rest/page/item-edit")
	public String showItemEdit(){
		return "item-edit";
	}
	/**
	 * 逻辑上删除商品
	 * @param ids 很多个id
	 * @return 
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult egoResult = new EgoResult();
		int index = tbItemService.updByItem(ids, (byte)3);
		if(index == 1){
			egoResult.setStatus(200);
		}
		return egoResult;
	}
	/**
	 * 逻辑上架商品
	 * @param ids 很多个id
	 * @return 
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public EgoResult reshelf(String ids){
		EgoResult egoResult = new EgoResult();
		int index = tbItemService.updByItem(ids, (byte)1);
		if(index == 1){
			egoResult.setStatus(200);
		}
		return egoResult;
	}
	/**
	 * 逻辑下架商品
	 * @param ids 很多个id
	 * @return 
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public EgoResult instock(String ids){
		EgoResult egoResult = new EgoResult();
		int index = tbItemService.updByItem(ids, (byte)2);
		if(index == 1){
			egoResult.setStatus(200);
		}
		return egoResult;
	}
	
	/**
	 * 商品新增
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("item/save")
	@ResponseBody
	public EgoResult insert(TbItem item,String desc,String itemParams){
		EgoResult er = new EgoResult();
		int index;
		try {
			index = tbItemService.insert(item, desc,itemParams);
			if(index == 1){
				er.setStatus(200);
			}
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			er.setData(e.getMessage());
		}
		return er;
	}
	
	/**
	 * 编辑数据商品
	 * @param tbItem
	 * @param tbItemDesc
	 * @param paramItem
	 * @return
	 */
	@RequestMapping("rest/item/update")
	@ResponseBody
	public int edit(TbItem tbItem,String desc){
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(tbItem.getId());
		Date date = new Date();
		tbItemDesc.setUpdated(date);
		tbItemDesc.setCreated(date);
		tbItemDesc.setItemDesc(desc);
		return tbItemService.updById(tbItem, tbItemDesc);
	}
}
