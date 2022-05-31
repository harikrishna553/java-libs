package com.sample.app.datasource.transformations;

import java.net.URL;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.util.Collector;

import com.sample.app.csvreader.CsvReaderHelloWorld;

public class MaxTransformationDemo {
	
	public static class EmployeeCitySplitter implements FlatMapFunction<Tuple4<Integer, String, Integer, String>, Tuple2<String, Integer>> {

		@Override
		public void flatMap(Tuple4<Integer, String, Integer, String> value, Collector<Tuple2<String, Integer>> out)
				throws Exception {
			out.collect(new Tuple2<>(value.f3, value.f2));
		}

	}

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = MaxTransformationDemo.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		executionEnvironment.readCsvFile(resourceFilePath("emps.csv")).ignoreFirstLine()
				.types(Integer.class, String.class, Integer.class, String.class)
				.flatMap(new EmployeeCitySplitter())
				.groupBy(0)
				.max(1)
				.collect()
				.forEach(System.out::println);
	}

}

