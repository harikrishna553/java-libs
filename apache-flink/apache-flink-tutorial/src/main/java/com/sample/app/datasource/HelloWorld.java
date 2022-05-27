package com.sample.app.datasource;

import java.util.List;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class HelloWorld {

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSource<Integer> amounts = executionEnvironment.fromElements(2, 3, 5, 7, 11);

		List<Integer> collect = amounts.reduce((integer, t1) -> integer + t1).collect();

		collect.stream().forEach(System.out::println);
	}
}
