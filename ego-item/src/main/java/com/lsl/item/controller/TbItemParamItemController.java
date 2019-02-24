package com.lsl.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.item.service.TbItemParamItemService;

@Controller
public class TbItemParamItemController {

	@Resource
	private TbItemParamItemService tbItemParamItemService;
	@RequestMapping(value="item/param/{id}.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String showItemParam(@PathVariable(value="id") Long itemId){
		return tbItemParamItemService.showItemParam(itemId);
	}
}
