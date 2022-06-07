package com.sample.app.streams;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DataStreamHelloWorld {

	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
	
		DataStream<String> dataStream = executionEnvironment.fromElements("one", "two", "three");

		dataStream
		.map(data -> data.toUpperCase())
		.executeAndCollect()
		.forEachRemaining(System.out::println);

	}

}