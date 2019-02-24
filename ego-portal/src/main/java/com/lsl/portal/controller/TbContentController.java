package com.lsl.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lsl.portal.service.TbContentService;

@Controller
public class TbContentController {

	@Autowired
	private TbContentService tbContentService;
	@RequestMapping("/showBigPic")
	public String showBigPic(ModelMap map){
		map.addAttribute("ad1", tbContentService.selBigPic());
		return "index";
	}
}
