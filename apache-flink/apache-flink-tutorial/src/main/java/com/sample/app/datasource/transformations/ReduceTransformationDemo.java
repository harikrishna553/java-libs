package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.ExecutionEnvironment;

public class ReduceTransformationDemo {
	
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		executionEnvironment
		.fromElements(1, 2, 3, 4, 5)
		.reduce((a, b) -> a + b)
		.collect()
		.forEach(System.out::println);
	}

}

