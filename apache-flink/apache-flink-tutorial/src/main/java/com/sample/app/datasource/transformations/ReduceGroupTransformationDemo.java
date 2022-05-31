package com.sample.app.datasource.transformations;

import java.util.ArrayList;
import java.util.List;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

public class ReduceGroupTransformationDemo {

	public static class EmployeesByCityGroupReduceFunction
			implements GroupReduceFunction<Tuple3<Integer, String, String>, Tuple2<String, List<String>>> {

		@Override
		public void reduce(Iterable<Tuple3<Integer, String, String>> values,
				Collector<Tuple2<String, List<String>>> out) throws Exception {

			List<String> names = new ArrayList<>();
			String cityName = null;

			for (Tuple3<Integer, String, String> value : values) {
				if (cityName == null) {
					cityName = value.f2;
				}
				String name = value.f1;

				names.add(name);
			}

			out.collect(new Tuple2(cityName, names));

		}

	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		Tuple3 emp1 = new Tuple3<Integer, String, String>(1, "Krishna", "Bangalore");
		Tuple3 emp2 = new Tuple3<Integer, String, String>(2, "Ram", "Hyderabad");
		Tuple3 emp3 = new Tuple3<Integer, String, String>(3, "Abhi", "Bangalore");
		Tuple3 emp4 = new Tuple3<Integer, String, String>(4, "Bala", "Hyderabad");
		Tuple3 emp5 = new Tuple3<Integer, String, String>(5, "Deeraj", "Hyderabad");
		Tuple3 emp6 = new Tuple3<Integer, String, String>(6, "Ramana", "Bangalore");

		DataSet<Tuple3<Integer, String, String>> employees = executionEnvironment.fromElements(emp1, emp2, emp3, emp4, emp5,
				emp6);
		employees
		.groupBy(2)
		.reduceGroup(new EmployeesByCityGroupReduceFunction())
		.collect()
		.forEach(System.out::println);
	}

}
