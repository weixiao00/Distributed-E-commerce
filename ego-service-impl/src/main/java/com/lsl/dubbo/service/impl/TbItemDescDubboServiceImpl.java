package com.lsl.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lsl.dubbo.service.TbItemDescDubboService;
import com.lsl.ego.dao.TbItemDescMapper;
import com.lsl.pojo.TbItemDesc;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {

	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public int insTbItemDesc(TbItemDesc desc) {
		return tbItemDescMapper.insert(desc);
	}
	@Override
	public TbItemDesc selDescById(long itemId) {
		return tbItemDescMapper.selectByPrimaryKey(itemId);
	}

}
