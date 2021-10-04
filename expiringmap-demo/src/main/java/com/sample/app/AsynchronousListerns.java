package com.sample.app;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class AsynchronousListerns {
	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	public static void main(String args[]) throws InterruptedException {
		Map<Integer, Employee> expiringMap = ExpiringMap.builder().expiration(5, TimeUnit.SECONDS)
				.asyncExpirationListener((key, employee) -> ((Employee) employee).logOnExpire())
				.expirationPolicy(ExpirationPolicy.CREATED).build();

		expiringMap.put(1, new Employee(1, "Ram"));
		sleepNSeconds(10);

	}
}
