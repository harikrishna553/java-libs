package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.ExecutionEnvironment;

public class FilterTransformationDemo {
	
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);
		
		// Calculate square of all even numbers
		executionEnvironment.fromElements(2, 3, 5, 4, 6, 8)
		.filter(num -> (num % 2 == 0))
		.map(num -> num * num)
		.collect()
		.forEach(System.out::println);
		
		
	}

}
