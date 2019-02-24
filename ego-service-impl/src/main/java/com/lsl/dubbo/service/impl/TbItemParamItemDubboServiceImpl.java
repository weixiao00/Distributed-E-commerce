package com.lsl.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lsl.dubbo.service.TbItemParamItemDubboService;
import com.lsl.ego.dao.TbItemParamItemMapper;
import com.lsl.pojo.TbItemParamItem;
import com.lsl.pojo.TbItemParamItemExample;

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {

	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Override
	public TbItemParamItem showItemParam(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

}
