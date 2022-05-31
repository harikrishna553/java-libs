package com.sample.app.datasource;

import org.apache.flink.api.common.functions.GroupCombineFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class WordCountUsingCombineGroupTransformation {

	public static class WordCountCombiner
			implements GroupCombineFunction<Tuple2<String, Integer>, Tuple2<String, Integer>> {

		@Override
		public void combine(Iterable<Tuple2<String, Integer>> values, Collector<Tuple2<String, Integer>> out)
				throws Exception {
			String key = null;
			int count = 0;

			for (Tuple2<String, Integer> tuple : values) {
				key = tuple.f0;
				count++;
			}

			out.collect(new Tuple2<String, Integer>(key, count));
		}

	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSet<String> input = executionEnvironment.fromElements("one", "two", "three", "one", "three", "four");

		input.map(new MapFunction<String, Tuple2<String, Integer>>() {

			@Override
			public Tuple2<String, Integer> map(String value) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2<>(value, 1);
			}

		}).groupBy(0)
		.combineGroup(new WordCountCombiner())
		.collect()
		.forEach(tuple2 -> {
			System.out.println(tuple2);
		});;
	}

}