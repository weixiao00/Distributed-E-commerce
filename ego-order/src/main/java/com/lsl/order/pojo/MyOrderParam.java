package com.lsl.order.pojo;

import java.util.List;

import com.lsl.pojo.TbOrderItem;
import com.lsl.pojo.TbOrderShipping;

public class MyOrderParam {

	private int paymentType;
	
	private String payment;
	
	private TbOrderShipping orderShipping;
	
	private List<TbOrderItem> orderItems;

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "MyOrderParam [paymentType=" + paymentType + ", payment=" + payment + ", orderShipping=" + orderShipping
				+ ", orderItems=" + orderItems + "]";
	}
	
	
}
