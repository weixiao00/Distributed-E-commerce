package com.lsl.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lsl.commons.pojo.EgoResult;
import com.lsl.pojo.TbUser;

public interface TbUserService {

	/**
	 * 根据用户填写的用户名和密码查询mysql
	 * @param user
	 * @return
	 */
	EgoResult selUserByUser(TbUser user,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 */
	EgoResult getUserByToken(String token);
	
	/**
	 * 退出登录
	 * @param token
	 * @return
	 */
	EgoResult logout(String token,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 注册查看用户名是否可用
	 * @return
	 */
	EgoResult registerCheckUsername(String username);
	
	/**
	 * 注册查看手机号是否可用
	 * @param phone
	 * @return
	 */
	EgoResult registerCheckPhone(String phone);
	
	/**
	 * 注册插入用户信息
	 * @param user
	 * @return
	 */
	EgoResult registerInsUser(TbUser user);
}
