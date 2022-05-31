package com.sample.app.datasource.transformations;

import java.net.URL;

import org.apache.flink.api.java.ExecutionEnvironment;

import com.sample.app.csvreader.CsvReaderHelloWorld;

public class ProjectTransformationDemo {

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = CsvReaderHelloWorld.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		executionEnvironment.readCsvFile(resourceFilePath("emps.csv")).ignoreFirstLine()
				.types(Integer.class, String.class, Integer.class, String.class).project(1, 2).collect()
				.forEach(tuple -> {
					Object name = tuple.getField(0);
					Object age = tuple.getField(1);

					System.out.println("name : " + name + ", age : " + age);
				});

	}

}


