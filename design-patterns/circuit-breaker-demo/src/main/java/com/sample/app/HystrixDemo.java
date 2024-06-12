package com.sample.app;

import com.sample.app.services.MyOrderService;
import com.sample.app.services.TimeUtil;

public class HystrixDemo {
	public static void main(String[] args) {
		MyOrderService orderService = new MyOrderService();
		for (int i = 0; i < 20; i++) {
			System.out.println(orderService.getProductDetails());
			
			if(i > 10) {
				TimeUtil.sleep(1000);
			}
		}
	}
}
