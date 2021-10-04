package com.sample.app;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class MaxCacheSizeDemo {

	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	private static void printCache(Map<Integer, Employee> expiringMap) {

		System.out.println("\nPrinting all the elements of cache");
		expiringMap.forEach((key, value) -> {
			System.out.println("Key : " + key + " Value : " + value);
		});
	}

	public static void main(String args[]) throws InterruptedException {
		Map<Integer, Employee> expiringMap = ExpiringMap.builder().expiration(10, TimeUnit.SECONDS).maxSize(5)
				.expirationPolicy(ExpirationPolicy.CREATED).build();

		System.out.println("Adding 5 employees");

		for (int i = 1; i < 6; i++) {
			Employee emp = new Employee(i, "emp " + i);
			expiringMap.put(i, emp);
		}

		sleepNSeconds(3);

		printCache(expiringMap);

		System.out.println("\nAdd two more elements");
		expiringMap.put(6, new Employee(6, "emp6"));
		expiringMap.put(7, new Employee(7, "emp7"));

		printCache(expiringMap);
	}
}
