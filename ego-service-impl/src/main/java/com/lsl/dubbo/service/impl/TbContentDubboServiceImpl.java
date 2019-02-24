package com.lsl.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.dubbo.service.TbContentDubboService;
import com.lsl.ego.dao.TbContentMapper;
import com.lsl.pojo.TbContent;
import com.lsl.pojo.TbContentExample;

public class TbContentDubboServiceImpl implements TbContentDubboService {

	@Autowired
	private TbContentMapper tbContentMapper;
	@Override
	public EasyUIDataGrid selContentbyPage(long categoryId, int page, int rows) {
		PageHelper.startPage(page,rows);
		TbContentExample example = new TbContentExample();
		if(categoryId != 0){
			example.createCriteria().andCategoryIdEqualTo(categoryId);
		}
		List<TbContent> list = tbContentMapper.selectByExample(example);
		PageInfo<TbContent> pi = new PageInfo<>(list);
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		return datagrid;
	}
	@Override
	public int insTbContent(TbContent tbContent) {
		return tbContentMapper.insertSelective(tbContent);
	}
	@Override
	public int updTbContent(TbContent tbContent) {
		return tbContentMapper.updateByPrimaryKeySelective(tbContent);
	}
	@Override
	public int delTbContent(Long id) {
		return tbContentMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<TbContent> selByCount(int count, boolean isSort) {
		TbContentExample example = new TbContentExample();
		//排序
		if (isSort) {
			example.setOrderByClause("updated desc");
		}
		if (count != 0) {
			PageHelper.startPage(1, count);
			List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
			PageInfo<TbContent> pi = new PageInfo<>(list);
			return pi.getList();
		} else {
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
	}

}
