package com.lsl.item.pojo;

import java.util.List;

public class ItemParamItem {

	private String group;
	private List<ItemParamItemParams> params;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List<ItemParamItemParams> getParams() {
		return params;
	}
	public void setParams(List<ItemParamItemParams> params) {
		this.params = params;
	}
	@Override
	public String toString() {
		return "ItemParamItem [group=" + group + ", params=" + params + "]";
	}
	
}
