package com.lsl.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lsl.dubbo.service.TbUserDubboService;
import com.lsl.ego.dao.TbUserMapper;
import com.lsl.pojo.TbUser;
import com.lsl.pojo.TbUserExample;

public class TbUserDubboServiceImpl implements TbUserDubboService {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Override
	public TbUser selTbUserByUser(TbUser user) {
		TbUserExample example = new TbUserExample();
		if (user.getPassword() != null && user.getUsername() != null) {
			example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
		} else if(user.getUsername() != null) {
			example.createCriteria().andUsernameEqualTo(user.getUsername());
		} else if(user.getPhone() != null){
			example.createCriteria().andPhoneEqualTo(user.getPhone());
		}
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public int insUser(TbUser user) {
		return tbUserMapper.insertSelective(user);
	}

}
