package com.sample.app.services;

public class ProductService {
    public String callService() {
        // Simulate a delay or failure
        if (Math.random() > 0.7) {
            throw new RuntimeException("ProductService is down");
        }
        return "ProductService response";
    }
}
