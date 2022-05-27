package com.sample.app.datasource;

import java.nio.charset.StandardCharsets;

import org.apache.flink.api.java.ExecutionEnvironment;

public class SpecifyEncodingWhileReadingFile {
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		final String filePath = "/Users/Shared/data.txt";

		executionEnvironment.readTextFile(filePath, StandardCharsets.UTF_8.name()).print();

	}
}
