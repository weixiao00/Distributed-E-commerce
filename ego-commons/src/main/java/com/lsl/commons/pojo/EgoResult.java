package com.lsl.commons.pojo;

/**
 * 上架，下架，被删除成功后返回的状态码
 * @author 123
 *
 */
public class EgoResult {

	private int status;

	private Object data;
	
	private String msg;
	
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "EgoResult [status=" + status + ", data=" + data + ", msg=" + msg + "]";
	}
	
	
	
}
