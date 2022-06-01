package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;

public class DistictDataSetUsingCustomKeySelector {

	private static class HashCodeSelector implements KeySelector<String, Integer> {
		private static final long serialVersionUID = 1L;

		@Override
		public Integer getKey(String value) throws Exception {
			return value.hashCode();
		}

	}

	public static void main(String[] args) throws Exception {

		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		executionEnvironment
		.fromElements("Hi", "Hello", "Hi", "Hi!!!", "Hello")
		.distinct(new HashCodeSelector())
				.collect()
				.forEach(System.out::println);
	}

}
