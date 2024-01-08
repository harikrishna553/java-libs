package com.sample.app;

import com.sample.app.model.Employee;
import com.sample.app.util.EmployeeSharding;

public class EmployeeShardingDemo {
	
	public static void main(String[] args) {
		EmployeeSharding sharding = new EmployeeSharding(3);

		// Adding some employees
		sharding.addEmployee(new Employee(1, "Ram"));
		sharding.addEmployee(new Employee(2, "Hari"));
		sharding.addEmployee(new Employee(3, "Krishna"));
		sharding.addEmployee(new Employee(4, "Harika"));
		sharding.addEmployee(new Employee(5, "Sailu"));

		// Print the shards
		sharding.printShards();
	}
}
