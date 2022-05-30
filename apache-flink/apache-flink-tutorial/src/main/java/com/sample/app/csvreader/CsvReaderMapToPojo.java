package com.sample.app.csvreader;

import java.net.URL;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;

import com.sample.app.dto.Employee;

public class CsvReaderMapToPojo {

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = CsvReaderHelloWorld.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		CsvReader csvReader = new CsvReader(resourceFilePath("emps.csv"), executionEnvironment).ignoreFirstLine();

		DataSource<Employee> dataSource = csvReader.pojoType(Employee.class, "empId", "empName", "empAge", "empCity");

		dataSource.collect().forEach(System.out::println);
	}

}
