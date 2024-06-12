package com.sample.app.services;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
	public static void sleep(int noOfMilliseconds) {
    	try {
			TimeUnit.MILLISECONDS.sleep(noOfMilliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
