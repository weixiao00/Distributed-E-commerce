package com.lsl.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.pojo.EasyUiTree;
import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.util.IDUtils;
import com.lsl.dubbo.service.TbContentCategoryDubboService;
import com.lsl.manage.service.TbContentCategoryService;
import com.lsl.pojo.TbContentCategory;
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboService;
	@Override
	public List<EasyUiTree> showCategory(long id) {
		List<EasyUiTree> listTree = new ArrayList<>();
		List<TbContentCategory> list = tbContentCategoryDubboService.selByPid(id);
		for(TbContentCategory category:list){
			EasyUiTree tree = new EasyUiTree();
			tree.setId(category.getId());
			tree.setText(category.getName());
			tree.setState(category.getIsParent()?"closed":"open");
			listTree.add(tree);
		}
		return listTree;
	}
	@Override
	public EgoResult create(TbContentCategory category) {
		EgoResult er = new EgoResult();
		List<TbContentCategory> list = tbContentCategoryDubboService.selByPid(category.getParentId());
		for(TbContentCategory tc:list){
			if(tc.getName().equals(category.getName())){
				er.setData("该类目已经存在");
				return er;
			}
		}
		long id = IDUtils.genItemId();
		category.setId(id);
		Date date = new Date();
		category.setCreated(date);
		category.setUpdated(date);
		category.setIsParent(false);
		category.setSortOrder(1);
		category.setStatus(1);
		int index = tbContentCategoryDubboService.insContentCategory(category);
		if (index > 0) {
			TbContentCategory cate = new TbContentCategory();
			cate.setId(category.getParentId());
			cate.setIsParent(true);
			tbContentCategoryDubboService.updIsParentById(cate);
		}
		er.setStatus(200);
		Map<String,Long> map = new HashMap();
		map.put("id", id);
		er.setData(map);
		return er;
	}
	@Override
	public EgoResult update(TbContentCategory category) {
		EgoResult er = new EgoResult();
		//查询当前节点的全部信息
		TbContentCategory selParentById = tbContentCategoryDubboService.selParentById(category.getId());
		//根据父类目id查询所有子类目
		List<TbContentCategory> list = tbContentCategoryDubboService.selByPid(selParentById.getParentId());
		for(TbContentCategory tc:list){
			if(tc.getName().equals(category.getName())){
				er.setData("该类目已经存在");
				return er;
			}
		}
		Date date = new Date();
		category.setCreated(date);
		category.setUpdated(date);
		int index = tbContentCategoryDubboService.updIsParentById(category);
		if(index > 0){
			er.setStatus(200);
		}
		return er;
	}
	@Override
	public EgoResult delete(TbContentCategory category) {
		EgoResult er = new EgoResult();
		category.setStatus(0);
		int index = tbContentCategoryDubboService.updIsParentById(category);
		if(index > 0){
			TbContentCategory selParentById = tbContentCategoryDubboService.selParentById(category.getId());
			List<TbContentCategory> list = tbContentCategoryDubboService.selByPid(selParentById.getParentId());
			if(list == null || list.size() == 0){
				TbContentCategory parent = new TbContentCategory();
				parent.setId(selParentById.getParentId());
				parent.setIsParent(false);
				int index2 = tbContentCategoryDubboService.updIsParentById(parent);
				if (index2 > 0) {
					er.setStatus(200);
				}
			}else{
				er.setStatus(200);
			}
		}
		return er;
	}

}
