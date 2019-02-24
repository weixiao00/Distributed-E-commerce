package com.lsl.item.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsl.commons.util.JsonUtils;
import com.lsl.dubbo.service.TbItemParamItemDubboService;
import com.lsl.item.pojo.ItemParamItem;
import com.lsl.item.pojo.ItemParamItemParams;
import com.lsl.item.service.TbItemParamItemService;
import com.lsl.pojo.TbItemParamItem;

import ego.lsl.redis.dao.JedisDao;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

	@Autowired
	private JedisDao jedisDao;
	@Value("${redis.item.param.key}")
	private String paramKey;
	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboService;
	@Override
	public String showItemParam(Long itemId) {
		//查redis
		String key = paramKey + itemId;
		if(jedisDao.exists(key)) {
			String value = jedisDao.get(key);
			if (value != null && !value.equals("")) {
				System.out.println("redis param");
				return value;
			}
		}
		TbItemParamItem itemParamItem = tbItemParamItemDubboService.showItemParam(itemId);
		StringBuffer sb = new StringBuffer();
		if (itemParamItem == null) {
			return "没有规格参数";
		}
		
		String paramData = itemParamItem.getParamData();
		List<ItemParamItem> list = JsonUtils.jsonToList(paramData, ItemParamItem.class);
		for (ItemParamItem itemParam : list) {
			sb.append("<table>");
			List<ItemParamItemParams> params = itemParam.getParams();
			for(int i=0;i<params.size();i++) {
				if(i == 0) {
					sb.append("<tr>");
					sb.append("<td width='100' align='right'>"+itemParam.getGroup()+"</td>");
					sb.append("<td width='200' align='right'>"+params.get(i).getK()+"</td>");
					sb.append("<td width='300' align='left'>"+params.get(i).getV()+"</td>");
					sb.append("</tr>");
				} else {
					sb.append("<tr>");
					sb.append("<td></td>");
					sb.append("<td align='right'>"+params.get(i).getK()+"</td>");
					sb.append("<td align='left'>"+params.get(i).getV()+"</td>");
					sb.append("</tr>");
				}
			}
			sb.append("</table>");
			sb.append("<hr/>");
		}
		//同步到redis里
		jedisDao.set(key, sb.toString());
		return sb.toString();
	}

}
