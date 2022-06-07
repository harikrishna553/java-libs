package com.sample.app.streams;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DataStreamFromSequenceDemo {
	
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);
		
		executionEnvironment.fromSequence(2, 20)
		.executeAndCollect()
		.forEachRemaining(System.out::println);
	}

}

