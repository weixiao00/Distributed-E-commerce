package com.lsl.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.item.service.TbItemCatService;

@Controller
public class TbItemCatController {

	@Autowired
	private TbItemCatService tbItemCatService;
	/**
	 * 返回jsonp的数据格式，包括所有的菜单
	 * @param callback
	 * @return
	 */
	@RequestMapping("/rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue showMenu(String callback){
		MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatService.showcatMenu());
		mjv.setJsonpFunction(callback);
		return mjv;
	}
}
