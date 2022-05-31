package com.sample.app.datasource.transformations;

import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.java.ExecutionEnvironment;

import com.sample.app.dto.Employee;

public class MapTransoformationDemo {

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		List<Employee> emps = Arrays.asList(new Employee(1, "Krishna", 33, "Bangalore"),
				new Employee(2, "Raheem", 35, "Mumbai"));

		executionEnvironment.fromCollection(emps).map(emp -> {
			Integer id = emp.getEmpId();
			String name = emp.getEmpName();
			String city = emp.getEmpCity();
			Integer age = emp.getEmpAge();

			return id + "," + name + "," + city + "," + age;
		}).collect().forEach(System.out::println);

	}

}