package com.lsl.manage.service;

import java.util.List;

import com.lsl.commons.pojo.EasyUiTree;

public interface TbItemCatService {
 
	/**
	 * 根据父菜单id显示所有的子菜单
	 * @param id
	 * @return
	 */
	List<EasyUiTree> show(long pid);
}
