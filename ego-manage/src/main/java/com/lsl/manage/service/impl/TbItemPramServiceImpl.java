package com.lsl.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.EasyUIDataGrid;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.dubbo.service.TbItemCatDubboService;
import com.lsl.dubbo.service.TbItemParamDubboService;
import com.lsl.manage.pojo.TbItemParamChild;
import com.lsl.manage.service.TbItemParamService;
import com.lsl.pojo.TbItemParam;
@Service
public class TbItemPramServiceImpl implements TbItemParamService {

	@Reference
	private TbItemParamDubboService tbItemParamDubboService;
	
	@Reference
	private TbItemCatDubboService tbItemCatDubboService;
	@Override
	public EasyUIDataGrid showPagePram(int rows, int page) {
		EasyUIDataGrid datagrid = tbItemParamDubboService.showPageParam(page, rows);
		List<TbItemParam> list = (List<TbItemParam>)datagrid.getRows();
		List<TbItemParamChild> listChild = new ArrayList<>();
		for(TbItemParam tbItemParam : list){
			TbItemParamChild child = new TbItemParamChild();
			child.setCreated(tbItemParam.getCreated());
			child.setId(tbItemParam.getId());
			child.setItemCatId(tbItemParam.getItemCatId());
			child.setUpdated(tbItemParam.getUpdated());
			child.setParamData(tbItemParam.getParamData());
			child.setItemCatName(tbItemCatDubboService.dataCat(tbItemParam.getItemCatId()).getName());
			listChild.add(child);
		}
		datagrid.setRows(listChild);
		return datagrid;
	}
	@Override
	public int delete(String ids) throws Exception {
		return tbItemParamDubboService.delByIds(ids);
	}
	@Override
	public EgoResult showParam(long catId) {
		EgoResult er = new EgoResult();
		TbItemParam tbItemParam = tbItemParamDubboService.selByCatId(catId);
		if(tbItemParam != null){
			er.setStatus(200);
			er.setData(tbItemParam.getParamData());
		}
		return er;
	}
	@Override
	public EgoResult save(TbItemParam param) {
		EgoResult er = new EgoResult();
		Date date = new Date();
		param.setCreated(date);
		param.setUpdated(date);
		int index = tbItemParamDubboService.insTbItemParam(param);
		if(index > 0){
			er.setStatus(200);
			return er;
		}
		return er;
	}

}
