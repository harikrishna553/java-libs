package com.sample.app.datasource;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class GetDataSourceFromGivenElements {
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		DataSource<Integer> dataSource = executionEnvironment.fromElements(2, 3, 5, 7, 11);
		dataSource.collect().forEach(System.out::println);

	}
}
