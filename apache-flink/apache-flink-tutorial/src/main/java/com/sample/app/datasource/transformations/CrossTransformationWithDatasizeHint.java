package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class CrossTransformationWithDatasizeHint {

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSource<Integer> dataSet1 = executionEnvironment.fromElements(2, 3, 5, 7, 11);

		DataSource<Integer> dataSet2 = executionEnvironment.fromElements(5, 6);

		dataSet1
		.crossWithTiny(dataSet2)
		.collect()
		.forEach(System.out::println);

	}

}

