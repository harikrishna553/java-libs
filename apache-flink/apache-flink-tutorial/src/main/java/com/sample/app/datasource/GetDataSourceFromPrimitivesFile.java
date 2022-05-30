package com.sample.app.datasource;

import java.net.URL;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class GetDataSourceFromPrimitivesFile {

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = GetDataSourceFromPrimitivesFile.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		final DataSource<Integer> fileContent1 = executionEnvironment
				.readFileOfPrimitives(resourceFilePath("primes.txt"), Integer.class);
		fileContent1.collect().stream().forEach(System.out::println);

		final DataSource<Integer> fileContent2 = executionEnvironment
				.readFileOfPrimitives(resourceFilePath("primesDelimited.txt"), ",", Integer.class);
		fileContent2.collect().stream().forEach(System.out::println);
	}
}