package com.lsl.passport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.commons.pojo.EgoResult;
import com.lsl.passport.service.TbUserService;
import com.lsl.pojo.TbUser;

@Controller
public class TbUserController {

	@Autowired
	private TbUserService tbUserService;
	/**
	 * 调到登录界面
	 * @param referer
	 * @param map
	 * @return
	 */
	@RequestMapping("/user/showLogin")
	public String welcome(@RequestHeader(value="Referer",defaultValue="") String referer,ModelMap map,String url){
		if ("http://localhost:8084/user/showRegister".equals(referer)) {
			referer = "";
		}
		if (url != null && !url.equals("")) {
			referer = url;
			System.out.println(referer);
		}
		map.addAttribute("redirect", referer);
		return "login";
	}
	
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping("/user/showRegister")
	public String register() {
		return "register";
	}
	
	/**
	 * 注册检验，用户名，手机号是否可用
	 * @param param
	 * @param n
	 * @return
	 */
	@RequestMapping("/user/check/{param}/{n}")
	@ResponseBody
	public EgoResult register(@PathVariable(value="param") String param,@PathVariable(value="n") int n) {
		if (n == 1) {
			return tbUserService.registerCheckUsername(param);
		} else if (n == 2) {
			return tbUserService.registerCheckPhone(param);
		}
		return null;
	}
	/**
	 * 单点登录
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public EgoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response){
		return tbUserService.selUserByUser(user,request,response);
	}
	
	/**
	 * 其他模块访问，实现单点登录
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUser(@PathVariable(value="token") String token,String callback) {
		EgoResult er = tbUserService.getUserByToken(token);
		if (callback != null && !callback.equals("")){
			System.out.println(callback);
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		} else {
			return er;
		}
	}
	
	/**
	 * 退出登录
	 * @param token
	 * @param callback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable(value="token") String token,String callback,HttpServletRequest request,HttpServletResponse response) {
		EgoResult er = tbUserService.logout(token, request, response);
		if (callback != null && !callback.equals("")){
			System.out.println(callback);
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		} else {
			return er;
		}
	}
	
	/**
	 * 注册，插入用户信息数据
	 * @param user
	 * @return
	 */
	@RequestMapping("/user/register")
	@ResponseBody
	public EgoResult registerInsUser(TbUser user) {
		return tbUserService.registerInsUser(user);
	}
}
