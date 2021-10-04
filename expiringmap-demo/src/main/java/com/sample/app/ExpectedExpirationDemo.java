package com.sample.app;

import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class ExpectedExpirationDemo {

	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	public static void main(String args[]) throws InterruptedException {

		ExpiringMap<Integer, Employee> expiringMap = ExpiringMap.builder().expiration(5, TimeUnit.SECONDS)
				.expirationPolicy(ExpirationPolicy.CREATED).build();

		expiringMap.put(1, new Employee(1, "Employee" + 1));
		sleepNSeconds(2);

		expiringMap.put(2, new Employee(2, "Employee" + 2));

		long expectedExpiration1 = expiringMap.getExpectedExpiration(1);
		long expectedExpiration2 = expiringMap.getExpectedExpiration(2);

		System.out.println("Expected exiry time for entry1 " + expectedExpiration1 +  " milli seconds");
		System.out.println("Expected exiry time for entry2 " + expectedExpiration2 + " milli seconds");

	}
}
