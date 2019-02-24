package com.lsl.dubbo.service;

import java.util.List;

import com.lsl.pojo.TbItemCat;

public interface TbItemCatDubboService {

	/**
	 * 根据父类目id查询所有子类目
	 * @param pid
	 * @return
	 */
	List<TbItemCat> show(long pid);
	
	/**
	 * 根据id查询商品类目
	 * @param id
	 * @return
	 */
	TbItemCat dataCat(long id);
}
