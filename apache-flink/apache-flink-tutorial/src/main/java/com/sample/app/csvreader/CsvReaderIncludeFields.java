package com.sample.app.csvreader;

import java.net.URL;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;

public class CsvReaderIncludeFields {

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = CsvReaderHelloWorld.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	private static void printDataSource(DataSource<Tuple3<Integer, String, String>> dataSource) throws Exception {
		dataSource.collect().forEach(tuple4 -> {
			Integer id = tuple4.f0;
			String name = tuple4.f1;
			String city = tuple4.f2;

			System.out.print("id : " + id + ",");
			System.out.print("name : " + name + ",");
			System.out.print("city : " + city + "\n");
		});

		System.out.println("\n");
	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		CsvReader csvReader = new CsvReader(resourceFilePath("emps.csv"), executionEnvironment).ignoreFirstLine()
				.includeFields(true, true, false, true);

		DataSource<Tuple3<Integer, String, String>> dataSource = csvReader.types(Integer.class, String.class,
				String.class);
		printDataSource(dataSource);

		// 2nd overloaded method
		csvReader = new CsvReader(resourceFilePath("emps.csv"), executionEnvironment).ignoreFirstLine()
				.includeFields("ttft");

		dataSource = csvReader.types(Integer.class, String.class, String.class);
		printDataSource(dataSource);

		// 3rd overloaded method
		csvReader = new CsvReader(resourceFilePath("emps.csv"), executionEnvironment).ignoreFirstLine()
				.includeFields("1101");

		dataSource = csvReader.types(Integer.class, String.class, String.class);
		printDataSource(dataSource);

	}

}