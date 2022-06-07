package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class CrossTransformationDemo {
	
	public static void main(String[] args) throws Exception {
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		DataSource<Integer> numbers1 = executionEnvironment.fromElements(2, 3);
		
		DataSource<Integer> numbers2 = executionEnvironment.fromElements(5, 6);
		
		numbers1
		.cross(numbers2)
		.collect()
		.forEach(System.out::println);
		
	}

}