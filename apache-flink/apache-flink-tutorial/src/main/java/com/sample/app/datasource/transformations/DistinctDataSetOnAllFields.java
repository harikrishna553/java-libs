package com.sample.app.datasource.transformations;

import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.java.ExecutionEnvironment;

import com.sample.app.dto.Employee;

public class DistinctDataSetOnAllFields {
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		Employee emp1 = new Employee(1, "Ram", 31, "Bangalore");
		Employee emp2 = new Employee(2, "Ravi", 31, "Hyderabad");
		Employee emp3 = new Employee(3, "Ram", 33, "Bangalore");
		Employee emp4 = new Employee(4, "Siva", 35, "Hyderabad");
		Employee emp5 = new Employee(1, "Ram", 31, "Bangalore");
		Employee emp6 = new Employee(6, "Kishore", 31, "Hyderabad");
		Employee emp7 = new Employee(7, "Krishna", 31, "Hyderabad");
		Employee emp8 = new Employee(1, "Ram", 31, "Bangalore");
		Employee emp9 = new Employee(2, "Ravi", 31, "Hyderabad");
		
		List<Employee> emps = Arrays.asList(emp1, emp2, emp3, emp4, emp5, emp6, emp7, emp8, emp9);
		
		System.out.println("Get distinct employees");
		executionEnvironment.fromCollection(emps)
		.distinct()
		.collect()
		.forEach(emp -> System.out.println(emp));
		
	}

}