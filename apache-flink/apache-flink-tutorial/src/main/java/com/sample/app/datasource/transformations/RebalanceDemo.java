package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class RebalanceDemo {
	
	public static void main(String[] args) throws Exception {
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		DataSet<Long> numbers = executionEnvironment.generateSequence(1, 10000);
		
		numbers
		.rebalance()
		.filter(number -> {
			return (number % 3333) == 0;
			})
		.collect()
		.forEach(System.out::println);
	}

}