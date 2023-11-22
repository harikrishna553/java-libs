package com.sample.app.util;

import java.util.concurrent.TimeUnit;

public class TimerUtil {
	
	public static void sleepNMilliseconds(long n) {
		try {
			TimeUnit.MILLISECONDS.sleep(n);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
