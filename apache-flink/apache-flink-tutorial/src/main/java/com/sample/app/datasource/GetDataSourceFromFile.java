package com.sample.app.datasource;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class GetDataSourceFromFile {
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		final String filePath = "/Users/Shared/data.txt";
		
		final DataSource<String> fileContent = executionEnvironment.readTextFile(filePath);
		fileContent.collect().stream().forEach(System.out::println);

	}
}
