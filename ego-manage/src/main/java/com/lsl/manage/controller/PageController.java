package com.lsl.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	public String welcome(){
		return "index";
	}
	@RequestMapping("{page}")
	public String page(@PathVariable String page){
		System.out.println("WWW");
		return page;
	}
}
