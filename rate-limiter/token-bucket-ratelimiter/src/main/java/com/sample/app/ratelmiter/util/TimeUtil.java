package com.sample.app.ratelmiter.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
	
	public static int milliSecondsForSeconds(int n) {
		return n * 1000;
	}
	
	public static void sleepNSeconds(int noOfSeconds) {
		try {
			TimeUnit.SECONDS.sleep(noOfSeconds);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	public static void sleepNMilliSeconds(int noOfMilliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(noOfMilliseconds);
		} catch (InterruptedException e) {
			// ignore
		}
	}
}
