package com.lsl.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lsl.dubbo.service.TbContentCategoryDubboService;
import com.lsl.ego.dao.TbContentCategoryMapper;
import com.lsl.pojo.TbContentCategory;
import com.lsl.pojo.TbContentCategoryExample;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<TbContentCategory> selByPid(long pid) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int insContentCategory(TbContentCategory category) {
		return tbContentCategoryMapper.insertSelective(category);
	}

	@Override
	public int updIsParentById(TbContentCategory category) {
		return tbContentCategoryMapper.updateByPrimaryKeySelective(category);
	}

	@Override
	public TbContentCategory selParentById(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}
	
}
