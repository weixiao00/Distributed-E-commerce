package com.lsl.passport.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.util.CookieUtils;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbUserDubboService;
import com.lsl.passport.service.TbUserService;
import com.lsl.pojo.TbUser;

import ego.lsl.redis.dao.JedisDao;

@Service
public class TbUserServiceImpl implements TbUserService {

	@Autowired
	private JedisDao jedisDao;
	@Reference
	private TbUserDubboService tbUserDubboService;
	@Override
	public EgoResult selUserByUser(TbUser user,HttpServletRequest request,HttpServletResponse response) {
		EgoResult er = new EgoResult();
		TbUser tbUser = tbUserDubboService.selTbUserByUser(user);
		if (tbUser != null) {
			//放到redis里key是UUID，value是用户信息
			String key = UUID.randomUUID().toString();
			jedisDao.set(key, JsonUtils.objectToJson(tbUser));
			jedisDao.expire(key, 60*60*24*1);
			//产生cookie给浏览器
			CookieUtils.setCookie(request, response, "TT_TOKEN", key);
			er.setStatus(200);
			return er;
		} else {
			er.setMsg("用户名的密码错误");
			return er;
		}
	}
	@Override
	public EgoResult getUserByToken(String token) {
		EgoResult er = new EgoResult();
		if (token != null && !token.equals("-1")) {
			String value = jedisDao.get(token);
			er.setStatus(200);
			TbUser user = JsonUtils.jsonToPojo(value, TbUser.class);
			//设置密码为空防止用户直接访问，得到密码
			System.out.println(token+"aaaaaa");
			user.setPassword(null);
			er.setData(user);
			er.setMsg("OK");
			return er;
		} else {
			er.setMsg("获取失败");
			return er;
		}
	}
	@Override
	public EgoResult logout(String token,HttpServletRequest request,HttpServletResponse response) {
		EgoResult er = new EgoResult();
		jedisDao.del(token);
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		er.setStatus(200);
		er.setMsg("OK");
		return er;
	}
	@Override
	public EgoResult registerCheckUsername(String username) {
		EgoResult er = new EgoResult();
		er.setStatus(200);
		er.setMsg("OK");
		TbUser user = new TbUser();
		user.setUsername(username);
		TbUser tbUser = tbUserDubboService.selTbUserByUser(user);
		if (tbUser == null) {
			er.setData(true);
		} else {
			er.setData(false);
		}
		return er;
	}
	@Override
	public EgoResult registerCheckPhone(String phone) {
		EgoResult er = new EgoResult();
		er.setStatus(200);
		er.setMsg("OK");
		TbUser user = new TbUser();
		user.setPhone(phone);
		TbUser tbUser = tbUserDubboService.selTbUserByUser(user);
		if (tbUser == null) {
			er.setData(true);
		} else {
			er.setData(false);
		}
		return er;
	}
	@Override
	public EgoResult registerInsUser(TbUser user) {
		EgoResult er = new EgoResult();
		Date date = new Date();
		user.setCreated(date);
		user.setUpdated(date);
		int index = tbUserDubboService.insUser(user);
		if (index == 1) {
			er.setStatus(200);
			return er;
		}
		return er;
	}
	

}
