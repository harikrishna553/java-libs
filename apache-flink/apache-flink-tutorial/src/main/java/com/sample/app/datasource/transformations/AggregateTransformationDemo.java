package com.sample.app.datasource.transformations;

import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;

import com.sample.app.splitter.StringSplitter;

public class AggregateTransformationDemo {

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		List<String> lines = Arrays.asList("Technology is best when it brings people together.",
				"The Web as I envisaged it, we have not seen it yet",
				"It is only when they go wrong that machines remind you how powerful they are");
		
		executionEnvironment
		.fromCollection(lines)
		.flatMap(new StringSplitter())
		.groupBy(0)
		.aggregate(Aggregations.SUM, 1)
		.collect()
		.forEach(tuple -> {
			System.out.println("'" + tuple.getField(0) + "' repeated " + tuple.getField(1) + " times");
		});

	}

}