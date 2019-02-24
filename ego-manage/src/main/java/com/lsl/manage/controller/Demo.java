package com.lsl.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class Demo {

	@RequestMapping(value="demo")
//	@ResponseBody
	public void demo(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.setCharacterEncoding("GB2312");
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
//		response.setCharacterEncoding("utf-8");
//		response.setHeader("Content-Type", "text/html;charset=utf-8");
		pw.write("我是一个中国人");
//		return "我是一个中国人";
	}
}
