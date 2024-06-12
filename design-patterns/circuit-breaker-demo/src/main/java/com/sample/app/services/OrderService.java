package com.sample.app.services;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class OrderService {
    private ProductService productService = new ProductService();

    public String getProductDetails() {
        return new ProductServiceCommand(productService).execute();
    }

    private class ProductServiceCommand extends HystrixCommand<String> {
        private final ProductService prodService;

        protected ProductServiceCommand(ProductService prodService) {
            super(HystrixCommandGroupKey.Factory.asKey("ProductService"));
            this.prodService = prodService;
        }

        @Override
        protected String run() throws Exception {
            return prodService.callService();
        }

        @Override
        protected String getFallback() {
            return "Fallback response";
        }
    }
}
