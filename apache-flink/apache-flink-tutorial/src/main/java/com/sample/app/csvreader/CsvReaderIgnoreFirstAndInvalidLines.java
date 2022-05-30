package com.sample.app.csvreader;

import java.net.URL;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple4;

public class CsvReaderIgnoreFirstAndInvalidLines {

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = CsvReaderHelloWorld.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		CsvReader csvReader = new CsvReader(resourceFilePath("empsInvalidData.csv"), executionEnvironment).ignoreFirstLine()
				.ignoreInvalidLines();
		DataSource<Tuple4<Integer, String, Integer, String>> dataSource = csvReader.types(Integer.class, String.class,
				Integer.class, String.class);

		dataSource.collect().forEach(tuple4 -> {
			Integer id = tuple4.f0;
			String name = tuple4.f1;
			Integer age = tuple4.f2;
			String city = tuple4.f3;

			System.out.println("id : " + id);
			System.out.println("name : " + name);
			System.out.println("age : " + age);
			System.out.println("city : " + city + "\n");
		});
	}

}
