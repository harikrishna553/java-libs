package com.sample.app.datasource;

import org.apache.flink.api.java.ExecutionEnvironment;

public class ConvertDataSetToList {

	public static void main(String[] args) throws Exception {

		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		executionEnvironment.fromElements(2, 3, 5, 7, 11, 13, 17, 19).collect().stream().forEach(System.out::println);

	}

}
