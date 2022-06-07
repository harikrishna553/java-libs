package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.ExecutionEnvironment;

import com.sample.app.dto.Employee;

public class RangePartitionDemo  {
	public static void main(String[] args) throws Exception {

		Employee emp1 = new Employee(1, "Ram", 31, "Bangalore");
		Employee emp2 = new Employee(2, "Ravi", 31, "Hyderabad");
		Employee emp3 = new Employee(3, "Raheem", 43, "Bangalore");
		Employee emp4 = new Employee(4, "Joel", 35, "Hyderabad");
		Employee emp5 = new Employee(5, "Sailu", 31, "Bangalore");
		
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		executionEnvironment
		.fromElements(emp1, emp2, emp3, emp4, emp5)
		.partitionByRange("empCity")
		.collect()
		.forEach(System.out::println);
		
	}

}