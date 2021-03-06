package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class UnionTransformationDemo {
	public static void main(String[] args) throws Exception {
		
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		DataSource<Integer>  primeNumbersSet = executionEnvironment.fromElements(2, 3, 5, 7);
		DataSource<Integer>  numbers = executionEnvironment.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9);
		
		numbers
		.union(primeNumbersSet)
		.distinct()
		.collect()
		.forEach(System.out::println);
	}

}