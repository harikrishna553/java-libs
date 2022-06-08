package com.sample.app.streams;

import java.util.Arrays;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DataStreamFromIterator {
	
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		executionEnvironment
		.fromCollection(Arrays.asList(2, 3, 5, 7).iterator(), Integer.class)
		.executeAndCollect()
		.forEachRemaining(System.out::println);
	}

}