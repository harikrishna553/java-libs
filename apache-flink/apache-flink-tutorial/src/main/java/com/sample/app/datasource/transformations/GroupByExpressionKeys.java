package com.sample.app.datasource.transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import com.sample.app.dto.Employee;

public class GroupByExpressionKeys {

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

		executionEnvironment.fromCollection(emps)
		.groupBy("empCity").reduceGroup(new GroupReduceFunction<Employee, Tuple2<String, List<String>>> () {

			@Override
			public void reduce(Iterable<Employee> values, Collector<Tuple2<String, List<String>>> out)
					throws Exception {
				String city = null;
				List<String> empNames = new ArrayList<> ();
				for(Employee emp: values) {
					city = emp.getEmpCity();
					empNames.add(emp.getEmpName());
				}
				
				out.collect(new Tuple2<>(city, empNames));
			}

		
			
		}).collect().forEach(System.out::println);
	}
}
