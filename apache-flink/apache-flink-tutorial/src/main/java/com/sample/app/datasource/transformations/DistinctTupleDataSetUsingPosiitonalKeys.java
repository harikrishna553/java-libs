package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;

public class DistinctTupleDataSetUsingPosiitonalKeys {
	
	public static void main(String[] args) throws Exception {
		
		Tuple3 emp1 = new Tuple3<Integer, String, String>(1, "krishna", "Bangalore");
		Tuple3 emp2 = new Tuple3<Integer, String, String>(2, "Ram", "Bangalore");
		Tuple3 emp3 = new Tuple3<Integer, String, String>(3, "Ravi", "Hyderabad");
		Tuple3 emp4 = new Tuple3<Integer, String, String>(4, "Kishore", "Hyderabad");
		Tuple3 emp5 = new Tuple3<Integer, String, String>(5, "krishna", "Bangalore");
		Tuple3 emp6 = new Tuple3<Integer, String, String>(6, "Ravi", "Hyderabad");
		
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		System.out.println("Distinct employee name and city");
		
		// Get distinct names and city
		executionEnvironment
		.fromElements(emp1, emp2, emp3, emp4, emp5, emp6)
		.distinct(1, 2)
		.collect()
		.forEach(tuple3 -> System.out.println(tuple3.f1 + " , " + tuple3.f2));
		
		System.out.println("\nDistinct cities");
		
		// Get distinct cities
		executionEnvironment
		.fromElements(emp1, emp2, emp3, emp4, emp5, emp6)
		.distinct(2)
		.collect()
		.forEach(tuple3 -> System.out.println(tuple3.f2));		
	}

}


