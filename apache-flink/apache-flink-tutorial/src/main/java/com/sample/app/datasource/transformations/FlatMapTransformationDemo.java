package com.sample.app.datasource.transformations;

import java.util.Arrays;

import org.apache.flink.api.java.ExecutionEnvironment;

import com.sample.app.functions.StringToListOfChars;

public class FlatMapTransformationDemo {

	public static void main(String[] args) throws Exception {

		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		executionEnvironment.fromCollection(Arrays.asList("India", "China", "Australia")).flatMap(new StringToListOfChars())
				.collect().forEach(System.out::println);

	}

}