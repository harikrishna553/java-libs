package com.sample.app;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class CreatedExpirationPolicyDemo {
	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	public static void main(String args[]) throws InterruptedException {
		Map<Integer, Employee> expiringMap = ExpiringMap.builder().expiration(10, TimeUnit.SECONDS)
				.expirationPolicy(ExpirationPolicy.CREATED).build();

		expiringMap.put(1, new Employee(1, "Krishna"));

		sleepNSeconds(5);
		System.out.println("Entry with key 1 -> " + expiringMap.get(1));

		sleepNSeconds(6);
		System.out.println("Entry with key 1 -> " + expiringMap.get(1));

	}
}
