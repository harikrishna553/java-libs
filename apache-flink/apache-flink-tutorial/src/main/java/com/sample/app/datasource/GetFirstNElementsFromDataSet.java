package com.sample.app.datasource;

import org.apache.flink.api.java.ExecutionEnvironment;

public class GetFirstNElementsFromDataSet {
	
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		executionEnvironment.fromElements(2, 3, 5, 7, 11, 13, 19, 23)
		.first(3)
		.collect()
		.forEach(System.out::println);
	}

}
