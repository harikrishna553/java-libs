package com.sample.app.streams;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DataStreamFromGivenElements {
	
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);
		
		executionEnvironment.fromElements(2, 3, 5, 7)
		.executeAndCollect()
		.forEachRemaining(System.out::println);
	}

}