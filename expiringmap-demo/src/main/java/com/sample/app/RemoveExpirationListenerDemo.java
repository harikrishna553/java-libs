package com.sample.app;

import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.ExpirationListener;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class RemoveExpirationListenerDemo {

	private static void sleepNSeconds(int n) throws InterruptedException {
		System.out.println("\nSleeping for " + n + " seconds");
		TimeUnit.SECONDS.sleep(n);
	}

	public static void main(String args[]) throws InterruptedException {
		ExpiringMap<Integer, Employee> expiringMap = ExpiringMap.builder().expiration(5, TimeUnit.SECONDS)
				.expirationPolicy(ExpirationPolicy.CREATED).build();

		expiringMap.put(1, new Employee(1, "Ram"));

		System.out.println(("Adding expiration listener to the ExpiringMap"));
		ExpirationListener<Integer, Employee> empListener = new ExpirationListener<Integer, Employee>() {

			@Override
			public void expired(Integer key, Employee emp) {
				emp.logOnExpire();
			}
		};
		expiringMap.addExpirationListener(empListener);
		sleepNSeconds(7);

		System.out.println("\nAdding employee entry again");
		expiringMap.put(1, new Employee(1, "Ram"));

		System.out.println("\nRemoving the listener");
		expiringMap.removeExpirationListener(empListener);

		sleepNSeconds(10);

	}
}
