package com.sample.app.datasource.transformations;

import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.java.ExecutionEnvironment;

import com.sample.app.dto.Employee;

public class DistinctDataSetUsingExpressionKeys {
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		Employee emp1 = new Employee(1, "Ram", 31, "Bangalore");
		Employee emp2 = new Employee(2, "Ravi", 31, "Hyderabad");
		Employee emp3 = new Employee(3, "Ram", 31, "Bangalore");
		Employee emp4 = new Employee(4, "Siva", 31, "Hyderabad");
		Employee emp5 = new Employee(5, "Ram", 31, "Bangalore");
		Employee emp6 = new Employee(6, "Kishore", 31, "Hyderabad");
		Employee emp7 = new Employee(7, "Krishna", 31, "Hyderabad");
		Employee emp8 = new Employee(8, "Ram", 31, "Bangalore");
		Employee emp9 = new Employee(2, "Ravi", 31, "Hyderabad");
		
		List<Employee> emps = Arrays.asList(emp1, emp2, emp3, emp4, emp5, emp6, emp7, emp8, emp9);
		
		System.out.println("Get distinct employee names");
		executionEnvironment.fromCollection(emps)
		.distinct("empName")
		.collect()
		.forEach(emp -> System.out.println(emp.getEmpName()));
		
		System.out.println("\nGet distinct employee name and city");
		executionEnvironment.fromCollection(emps)
		.distinct("empName", "empCity")
		.collect()
		.forEach(emp -> System.out.println(emp.getEmpName() + "," + emp.getEmpCity()));
		
	}

}
