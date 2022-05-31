package com.sample.app.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class StringToListOfChars implements FlatMapFunction<String, List<Character>>{

	@Override
	public void flatMap(String value, Collector<List<Character>> out) throws Exception {
		List<Character> chars = new ArrayList<> ();
		
		for(char ch: value.toCharArray()) {
			chars.add(ch);
		}
		
		out.collect(chars);
	}

}
