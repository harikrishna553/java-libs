package com.sample.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.app.model.Employee;

public final class EmployeeSharding {

	private final Map<Integer, List<Employee>> shards;
	private final int numberOfShards;

	public EmployeeSharding(int numberOfShards) {
		shards = new HashMap<>();
		this.numberOfShards = numberOfShards;
	}

	public void addEmployee(Employee employee) {
		int shardKey = employee.getId() % numberOfShards;
		shards.computeIfAbsent(shardKey, k -> new ArrayList<>()).add(employee);
	}

	public void printShards() {
		for (Integer shard : shards.keySet()) {
			System.out.println("Shard " + shard + ": " + shards.get(shard));
		}
	}

}
