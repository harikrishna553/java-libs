package com.sample.app;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.jodah.expiringmap.ExpiringMap;
import com.sample.app.model.Employee;

public class HelloWorld {

	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	public static void main(String args[]) throws InterruptedException {
		Map<Integer, Employee> expiringMap = ExpiringMap.builder().maxSize(100).expiration(18, TimeUnit.SECONDS)
				.build();

		expiringMap.put(1, new Employee(1, "Krishna"));

		sleepNSeconds(5);
		System.out.println("Entry with key 1 -> " + expiringMap.get(1));

		sleepNSeconds(5);
		System.out.println("Entry with key 1 -> " + expiringMap.get(1));

		sleepNSeconds(5);
		System.out.println("Entry with key 1 -> " + expiringMap.get(1));

		sleepNSeconds(5);
		System.out.println("Entry with key 1 -> " + expiringMap.get(1));
	}
}
