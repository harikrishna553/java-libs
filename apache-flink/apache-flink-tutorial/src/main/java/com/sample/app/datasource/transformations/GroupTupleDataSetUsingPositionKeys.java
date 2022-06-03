package com.sample.app.datasource.transformations;

import java.util.ArrayList;
import java.util.List;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

public class GroupTupleDataSetUsingPositionKeys {

	public static void main(String[] args) throws Exception {
		Tuple3<Integer, String, String> emp1 = new Tuple3<Integer, String, String>(1, "krishna", "Bangalore");
		Tuple3<Integer, String, String> emp2 = new Tuple3<Integer, String, String>(2, "Ram", "Bangalore");
		Tuple3<Integer, String, String> emp3 = new Tuple3<Integer, String, String>(3, "Ravi", "Hyderabad");
		Tuple3<Integer, String, String> emp4 = new Tuple3<Integer, String, String>(4, "Kishore", "Hyderabad");
		Tuple3<Integer, String, String> emp5 = new Tuple3<Integer, String, String>(5, "krishna", "Bangalore");
		Tuple3<Integer, String, String> emp6 = new Tuple3<Integer, String, String>(6, "Ravi", "Hyderabad");
		
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
		
		
		executionEnvironment
		.fromElements(emp1, emp2, emp3, emp4, emp5, emp6)
		.groupBy(2)
		.reduceGroup(new GroupReduceFunction<Tuple3<Integer, String, String>, Tuple2<String, List<String>>> (){

			@Override
			public void reduce(Iterable<Tuple3<Integer, String, String>> values,
					Collector<Tuple2<String, List<String>>> out) throws Exception {
				String city = null;
				List<String> empNames = new ArrayList<> ();
				
				for(Tuple3<Integer, String, String> tuple3: values) {
					city = tuple3.f2;
					empNames.add(tuple3.f1);
				}
				
				out.collect(new Tuple2<> (city, empNames));
			}

			
			
		})
		.collect()
		.forEach(System.out::println);
	}
}
