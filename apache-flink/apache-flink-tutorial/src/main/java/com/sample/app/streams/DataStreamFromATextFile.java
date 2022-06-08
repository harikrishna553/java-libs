package com.sample.app.streams;

import java.net.URL;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.sample.app.csvreader.CsvReaderHelloWorld;

public class DataStreamFromATextFile {
	
	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = CsvReaderHelloWorld.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}
	
	
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		executionEnvironment
		.readTextFile(resourceFilePath("primes.txt"))
		.executeAndCollect()
		.forEachRemaining(System.out::println);
	}

}