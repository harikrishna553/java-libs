package com.sample.app;

import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class ResetExpirationTimeDemo {

	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	public static void main(String args[]) throws InterruptedException {

		ExpiringMap<Integer, Employee> expiringMap = ExpiringMap.builder().expiration(5, TimeUnit.SECONDS)
				.expirationPolicy(ExpirationPolicy.CREATED).build();

		expiringMap.put(1, new Employee(1, "Employee" + 1));
		sleepNSeconds(2);

		long expectedExpiration1 = expiringMap.getExpectedExpiration(1);
		System.out.println("Expected exiry time for entry1 " + expectedExpiration1 +  " milli seconds");
		
		expiringMap.resetExpiration(1);
		
		System.out.println("\nResetting the expiration time of the entry 1");

		expectedExpiration1 = expiringMap.getExpectedExpiration(1);
		System.out.println("\nExpected exiry time for entry1 " + expectedExpiration1 +  " milli seconds");
	}
}
