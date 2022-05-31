package com.sample.app.datasource;

import org.apache.flink.api.java.ExecutionEnvironment;

public class GetDataSourceFromSequenceOfNumbers {

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		executionEnvironment.generateSequence(10, 25).collect().forEach(System.out::println);
	}

}
