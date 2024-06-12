package com.sample.app.services;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommand.Setter;

public class MyOrderService {
	private static final Setter PRODUCT_SERVICE_HYSTRIC_CONFIG = Setter
			.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyProductService"))
			.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000)
					.withCircuitBreakerEnabled(true).withCircuitBreakerRequestVolumeThreshold(10)
					.withCircuitBreakerErrorThresholdPercentage(50).withCircuitBreakerSleepWindowInMilliseconds(5000)
					.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE));
	private MyProductService productService = new MyProductService();

	public String getProductDetails() {
		return new ProductServiceCommand(productService).execute();
	}

	private class ProductServiceCommand extends HystrixCommand<String> {
		private final MyProductService prodService;

		protected ProductServiceCommand(MyProductService prodService) {
			super(PRODUCT_SERVICE_HYSTRIC_CONFIG);
			this.prodService = prodService;
		}

		@Override
		protected String run() throws Exception {

			return prodService.callService();

		}

		@Override
		protected String getFallback() {
			return "Fallback response\n";
		}
	}
}


