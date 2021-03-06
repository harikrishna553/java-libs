package com.sample.app.dto;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
	private Integer id;
	private Integer customerId;
	private Date orderDate;

	public Order() {

	}

	public Order(Integer id, Integer customerId, Date orderDate) {
		this.id = id;
		this.customerId = customerId;
		this.orderDate = orderDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", orderDate=" + orderDate + "]";
	}

}
