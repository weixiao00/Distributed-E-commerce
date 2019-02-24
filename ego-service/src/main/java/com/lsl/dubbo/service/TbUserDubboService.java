package com.lsl.dubbo.service;

import com.lsl.pojo.TbUser;

public interface TbUserDubboService {

	/**
	 * 单点登录，根据用户信息，查询是否正确：注册时查看，用户名是否可用,查看手机号是否可用
	 * @param user
	 * @return
	 */
	TbUser selTbUserByUser(TbUser user);
	
	/**
	 * 注册插入用户数据信息
	 * @param user
	 * @return
	 */
	int insUser(TbUser user);
	
}
