package com.sample.app.streams;

import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.sample.app.csvreader.CsvReaderHelloWorld;

public class DataStreamFromATextFileAndSpecifyEncoding {
	
	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = CsvReaderHelloWorld.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}
	
	
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		executionEnvironment
		.readTextFile(resourceFilePath("primes.txt"), StandardCharsets.UTF_8.name())
		.executeAndCollect()
		.forEachRemaining(System.out::println);
	}

}

