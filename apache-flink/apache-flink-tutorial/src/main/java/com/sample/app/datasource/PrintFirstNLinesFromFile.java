package com.sample.app.datasource;

import org.apache.flink.api.java.ExecutionEnvironment;

public class PrintFirstNLinesFromFile {
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		final String filePath = "/Users/Shared/data.txt";
		
		executionEnvironment.readTextFile(filePath).first(2).print();
	}
}
