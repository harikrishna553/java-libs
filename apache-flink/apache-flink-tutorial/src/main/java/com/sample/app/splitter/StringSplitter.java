package com.sample.app.splitter;

import java.util.stream.Stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class StringSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

	@Override
	public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
		Stream.of(value.toLowerCase().split("\\W+")).filter(token -> token.length() > 0)
				.forEach(token -> out.collect(new Tuple2<>(token, 1)));
	}
}