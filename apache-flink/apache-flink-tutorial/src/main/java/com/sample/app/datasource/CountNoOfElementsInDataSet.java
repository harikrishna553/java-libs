package com.sample.app.datasource;

import org.apache.flink.api.java.ExecutionEnvironment;

public class CountNoOfElementsInDataSet {
	
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		long totalElements = executionEnvironment.fromElements(2, 3, 5, 7, 11, 13, 17, 19).count();
		
		System.out.println("totalElements : " + totalElements);
	}

}
