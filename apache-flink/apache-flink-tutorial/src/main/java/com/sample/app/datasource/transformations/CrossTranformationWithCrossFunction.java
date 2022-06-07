package com.sample.app.datasource.transformations;

import org.apache.flink.api.common.functions.CrossFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class CrossTranformationWithCrossFunction {

	private static class MultiplicationTable implements CrossFunction<Integer, Integer, String> {

		@Override
		public String cross(Integer val1, Integer val2) throws Exception {
			return new StringBuilder().append(val1).append(" * ").append(val2).append(" = ").append(val1 * val2)
					.toString();
		}

	}

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSource<Integer> numbers1 = executionEnvironment.fromElements(2, 3);

		DataSource<Integer> numbers2 = executionEnvironment.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		numbers1
		.cross(numbers2)
		.with(new MultiplicationTable())
		.collect()
		.forEach(System.out::println);

	}
}