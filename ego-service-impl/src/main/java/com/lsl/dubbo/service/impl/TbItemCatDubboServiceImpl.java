package com.lsl.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lsl.dubbo.service.TbItemCatDubboService;
import com.lsl.ego.dao.TbItemCatMapper;
import com.lsl.pojo.TbItemCat;
import com.lsl.pojo.TbItemCatExample;

public class TbItemCatDubboServiceImpl implements TbItemCatDubboService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Override
	public List<TbItemCat> show(long pid) {
		TbItemCatExample example = new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(pid);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		return list;
	}
	@Override
	public TbItemCat dataCat(long id) {
		return tbItemCatMapper.selectByPrimaryKey(id);
	}

}
