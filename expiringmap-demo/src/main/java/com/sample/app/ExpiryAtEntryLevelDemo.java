package com.sample.app;

import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class ExpiryAtEntryLevelDemo {
	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	public static void main(String args[]) throws InterruptedException {
		ExpiringMap<Integer, Employee> expiringMap = ExpiringMap.builder().variableExpiration()
				.expiration(10, TimeUnit.SECONDS).expirationPolicy(ExpirationPolicy.CREATED).build();

		expiringMap.put(1, new Employee(1, "Krishna"), ExpirationPolicy.ACCESSED);
		expiringMap.put(2, new Employee(2, "Ram"));

		sleepNSeconds(5);
		System.out.println("Entry with key 1 -> " + expiringMap.get(1));
		System.out.println("Entry with key 2 -> " + expiringMap.get(2));

		sleepNSeconds(8);
		System.out.println("\nEntry with key 1 -> " + expiringMap.get(1));
		System.out.println("Entry with key 2 -> " + expiringMap.get(2));

	}
}
