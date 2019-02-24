package com.lsl.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.dubbo.service.TbItemDubboService;
import com.lsl.ego.dao.TbItemDescMapper;
import com.lsl.ego.dao.TbItemMapper;
import com.lsl.ego.dao.TbItemParamItemMapper;
import com.lsl.pojo.TbItem;
import com.lsl.pojo.TbItemDesc;
import com.lsl.pojo.TbItemExample;
import com.lsl.pojo.TbItemParamItem;
//@Transactional
public class TbItemServiceImpl implements TbItemDubboService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	/**
	 * 显示所有数据
	 */
	@Override
	public EasyUIDataGrid show(int rows, int page) {
		//分页插件
		PageHelper.startPage(page, rows);
		List<TbItem> list = tbItemMapper.selAll();
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGrid eu = new EasyUIDataGrid();
		eu.setRows(pageInfo.getList());
		eu.setTotal(pageInfo.getTotal());
		return eu;
	}
	/**
	 * 商品的上架，下架，被删除功能
	 */
	@Override
	public int updByItem(TbItem tbItem) {
		return tbItemMapper.updateByPrimaryKeySelective(tbItem);
	}
	@Override
	public int insTbItem(TbItem tbItem) {
		return tbItemMapper.insert(tbItem);
	}
	@Override
	public int insTbItemDesc(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem paramItem) throws Exception {
		int index = 0;
			try {
				index = tbItemMapper.insert(tbItem);
				index += tbItemDescMapper.insert(tbItemDesc);
				index += tbItemParamItemMapper.insertSelective(paramItem);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(index == 3){
			return 1;
		}
		else{
			throw new Exception("新增失败");
		}
	}
	@Override
	public List<TbItem> selAllByStatus(byte status) {
		TbItemExample example = new TbItemExample();
		example.createCriteria().andStatusEqualTo(status);
		return tbItemMapper.selectByExample(example);
	}
	@Override
	public TbItem selById(long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updById(TbItem tbItem, TbItemDesc tbItemDesc) {
		int index = 0;
		index = tbItemMapper.updateByPrimaryKeySelective(tbItem);
		System.out.println(tbItemDesc.getItemId());
		index += tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
		if (index == 2){
			return 1;
		}
		return 0;
	}

}
