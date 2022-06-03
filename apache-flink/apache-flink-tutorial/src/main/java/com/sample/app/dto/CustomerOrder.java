package com.sample.app.dto;

import java.util.Date;

public class CustomerOrder {

	private Integer customerId;
	private Integer orderId;
	private Date orderDate;
	private String customerName;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String toString() {
		return "CustomerOrder [customerId=" + customerId + ", orderId=" + orderId + ", orderDate=" + orderDate
				+ ", customerName=" + customerName + "]";
	}

}
