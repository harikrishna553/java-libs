package com.sample.app.model;

import com.sample.app.enums.OrderStatus;
import com.sample.app.enums.PaymentType;

public class Order {
	private Integer orderId;
	private Integer customerId;
	private Double amount;
	private OrderStatus orderStatus;
	private PaymentType paymentType;

	public Order(Integer orderId, Integer customerId, Double amount, OrderStatus orderStatus, PaymentType paymentType) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.amount = amount;
		this.orderStatus = orderStatus;
		this.paymentType = paymentType;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customerId=" + customerId + ", amount=" + amount + ", orderStatus="
				+ orderStatus + ", paymentType=" + paymentType + "]";
	}

}
