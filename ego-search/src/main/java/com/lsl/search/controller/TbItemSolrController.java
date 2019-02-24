package com.lsl.search.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsl.pojo.TbItem;
import com.lsl.search.service.TbItemSolrService;

@Controller
public class TbItemSolrController {

	@Autowired
	private TbItemSolrService tbItemSolrService;
	/**
	 * solr初始化
	 * @return
	 */
	@RequestMapping(value="solr/init",produces="text/html;charset=utf-8")
	@ResponseBody
	public String init(){
		long start = System.currentTimeMillis();
		try {
			tbItemSolrService.init();
			long end = System.currentTimeMillis();
			return "初始化成功，使用了"+(end - start)/1000+"秒";
		} catch (Exception e) {
			e.printStackTrace();
			return "初始化失败";
		}
	}
	/**
	 * 商品搜索
	 * @param modelMap
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("search.html")
	public String search(ModelMap modelMap,String q,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="12") int rows){
		try {
			q = new String(q.getBytes("iso-8859-1"),"utf-8");
			Map<String, Object> map = tbItemSolrService.selBySolr(q, page, rows);
			modelMap.addAttribute("query", q);
			modelMap.addAttribute("itemList", map.get("itemList"));
			modelMap.addAttribute("totalPages", map.get("totalPages"));
			modelMap.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "search";
	}
	
	/**
	 * solr新增商品
	 * @param item
	 * @return 返回1成功，返回0失败
	 */
	@RequestMapping("solr/add")
	@ResponseBody
	public int addItem(@RequestBody TbItem item) {
		try {
			return tbItemSolrService.addItem(item);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 从solr里删除商品
	 * @param id
	 * @return
	 */
	@RequestMapping("solr/delete")
	@ResponseBody
	public int deleteSolrItem(@RequestBody String id){
		try {
			return tbItemSolrService.deleteSolrItem(id);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
