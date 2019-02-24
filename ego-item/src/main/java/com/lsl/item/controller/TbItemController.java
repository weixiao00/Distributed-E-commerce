package com.lsl.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lsl.item.service.TbItemService;

@Controller
public class TbItemController {

	@Autowired
	private TbItemService tbItemService;
	@RequestMapping("item/{id}.html")
	public String show(@PathVariable(value="id") Long id,ModelMap map){
		map.addAttribute("item", tbItemService.show(id));
		return "item";
	}
}
