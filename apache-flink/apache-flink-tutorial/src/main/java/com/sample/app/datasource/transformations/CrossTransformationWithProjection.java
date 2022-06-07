package com.sample.app.datasource.transformations;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

public class CrossTransformationWithProjection {

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSource<Tuple2<String, String>> dataSource1 = executionEnvironment.fromElements(new Tuple2("a", "ant"),
				new Tuple2("b", "ball"), new Tuple2("c", "cat"), new Tuple2("d", "dog"));

		DataSource<Tuple2<String, String>> dataSource2 = executionEnvironment.fromElements(new Tuple2("A", "ANT"),
				new Tuple2("B", "BALL"), new Tuple2("C", "CAT"), new Tuple2("D", "DOG"));

		DataSet<Tuple3<String, String, String>> resultSet = dataSource1
				.cross(dataSource2)
				.projectFirst(0)
				.projectSecond(0, 1);
		
		resultSet.collect().forEach(System.out::println);
	}

}