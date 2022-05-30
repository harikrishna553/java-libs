package com.sample.app.datasource;

import org.apache.flink.api.java.ExecutionEnvironment;

public class GetDataSourceOfStringValues {
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		final String filePath = "/Users/Shared/data.txt";

		executionEnvironment.readTextFileWithValue(filePath).print();

	}
}
