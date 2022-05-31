package com.sample.app.datasource;

import java.util.Arrays;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class GetDataSourceFromCollection {
	
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		DataSource<Integer> dataSource = executionEnvironment.fromCollection(Arrays.asList(30, 2, 12, 10, 9, 87, 65, 43));
		dataSource.filter(number -> (number > 15)).collect().forEach(System.out::println);
		
	}

}
