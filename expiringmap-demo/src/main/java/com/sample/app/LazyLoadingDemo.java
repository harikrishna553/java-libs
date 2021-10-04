package com.sample.app;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sample.app.model.Employee;

import net.jodah.expiringmap.EntryLoader;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public class LazyLoadingDemo {
	private static void printCache(Map<Integer, Employee> expiringMap) {

		System.out.println("\nPrinting all the elements of cache");
		expiringMap.forEach((key, value) -> {
			System.out.println("Key : " + key + " Value : " + value);
		});
	}

	public static void main(String args[]) throws InterruptedException {

		EntryLoader<Integer, Employee> entryLoader = new EntryLoader<Integer, Employee>() {

			@Override
			public Employee load(Integer key) {
				System.out.println("\nEmployee with the key " + key + " is not exist, loading lazily.....");
				return new Employee(key, "Employee " + key);
			}

		};

		ExpiringMap<Integer, Employee> expiringMap = ExpiringMap.builder().expiration(5, TimeUnit.SECONDS)
				.expirationPolicy(ExpirationPolicy.CREATED).entryLoader(entryLoader).build();

		expiringMap.put(1, new Employee(1, "Employee" + 1));
		printCache(expiringMap);

		System.out.println("Trying to get the entries for employee 2 and 3");

		System.out.println(expiringMap.get(2));
		System.out.println(expiringMap.get(3));

		printCache(expiringMap);
	}
}
