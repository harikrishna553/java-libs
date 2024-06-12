package com.sample.app;

import com.sample.app.services.OrderService;

public class FallbackResponse {
	public static void main(String[] args) {
		OrderService orderService = new OrderService();
		for (int i = 0; i < 50; i++) {
			System.out.println(orderService.getProductDetails());
		}
	}
}
