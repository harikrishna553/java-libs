package com.sample.app.services;

import java.util.Random;

public class MyProductService {
	private int counter = 1;
	
    public String callService() {
    	System.out.println("MyProductService called  for " + counter++ + " time");
    	
    	//int randomTime = 800 + new Random().nextInt(100, 500);
    	int randomTime = 1100;
    	TimeUtil.sleep(randomTime);
    	return "MyProductService finished";
        
    }
    
}
