package com.lsl.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.dubbo.service.TbItemParamDubboService;
import com.lsl.ego.dao.TbItemParamMapper;
import com.lsl.pojo.TbItemParam;
import com.lsl.pojo.TbItemParamExample;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Override
	public EasyUIDataGrid showPageParam(int page, int rows) {
		//开始分页
		PageHelper.startPage(page, rows);
		//查询数据加上分页插件的limit，完成查询功能
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		//将所有的封装到pageInfo里
		PageInfo<TbItemParam> pi = new PageInfo<>(list);
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		return datagrid;
	}
	@Override
	public int delByIds(String ids) throws Exception {
		int index = 0;
		String[] idstr = ids.split(",");
		for(String id : idstr){
			index += tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		if (index == idstr.length){
			return 1;
		}
		else{
			throw new Exception("删除失败，数据可能不存在");
		}
	}
	@Override
	public TbItemParam selByCatId(long catId) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catId);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0){
			//要求每个类目只有一个模板
//			System.out.println(list.get(0).getParamData());
			return list.get(0);
		}
		return null;
	}
	@Override
	public int insTbItemParam(TbItemParam itemParam) {
		return tbItemParamMapper.insertSelective(itemParam);
	}

}
