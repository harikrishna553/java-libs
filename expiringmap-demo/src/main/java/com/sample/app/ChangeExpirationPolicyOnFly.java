package com.sample.app;

import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class ChangeExpirationPolicyOnFly {
	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	public static void main(String args[]) throws InterruptedException {
		ExpiringMap<Integer, Employee> expiringMap = ExpiringMap.builder().expiration(6, TimeUnit.SECONDS)
				.expirationPolicy(ExpirationPolicy.CREATED).variableExpiration().build();

		expiringMap.put(1, new Employee(1, "Krishna"));
		expiringMap.put(2, new Employee(2, "Ram"));

		sleepNSeconds(4);
		System.out.println("Entry with key 1 -> " + expiringMap.get(1));
		System.out.println("Entry with key 2 -> " + expiringMap.get(2));

		System.out.println("\nSet the expiration policy of key 2 to ACCESSED\n");
		expiringMap.setExpirationPolicy(2, ExpirationPolicy.ACCESSED);

		System.out.println("\nEntry with key 1 -> " + expiringMap.get(1));
		System.out.println("Entry with key 2 -> " + expiringMap.get(2));
		
		sleepNSeconds(4);
		System.out.println("\nEntry with key 1 -> " + expiringMap.get(1));
		System.out.println("Entry with key 2 -> " + expiringMap.get(2));

	}
}
