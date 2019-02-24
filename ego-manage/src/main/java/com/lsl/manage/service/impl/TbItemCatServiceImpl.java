package com.lsl.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.EasyUiTree;
import com.lsl.dubbo.service.TbItemCatDubboService;
import com.lsl.manage.service.TbItemCatService;
import com.lsl.pojo.TbItemCat;
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

	@Reference
	private TbItemCatDubboService tbItemCatDubboService;
	@Override
	public List<EasyUiTree> show(long pid) {
		List<TbItemCat> list = tbItemCatDubboService.show(pid);
		List<EasyUiTree> listTree = new ArrayList<>();
		for(TbItemCat cat : list){
			EasyUiTree tree = new EasyUiTree();
			tree.setId(cat.getId());
			tree.setText(cat.getName());
			tree.setState(cat.getIsParent()?"closed":"open");
			listTree.add(tree);
		}
		return listTree;
	}

}
