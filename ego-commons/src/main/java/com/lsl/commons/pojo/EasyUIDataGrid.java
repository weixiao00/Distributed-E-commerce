package com.lsl.commons.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * 分页实现
 * @author 123
 *
 */
public class EasyUIDataGrid implements Serializable {

	//此页面记录数
	private List<?> rows;
	//数据库总记录数
	private long total;
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
}
