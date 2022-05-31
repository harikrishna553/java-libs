package com.sample.app.datasource.transformations;

import org.apache.flink.api.common.functions.GroupCombineFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

public class CombineGroupTransformationDemo {

	public static class EmployeesCountByCityGroupCombinerFunction
			implements GroupCombineFunction<Tuple3<Integer, String, String>, Tuple2<String, Integer>> {

		@Override
		public void combine(Iterable<Tuple3<Integer, String, String>> values, Collector<Tuple2<String, Integer>> out)
				throws Exception {
			String key = null;
			int count = 0;

			for (Tuple3<Integer, String, String> empData : values) {
				key = empData.f2;
				count++;
			}
			// emit tuple with word and count
			out.collect(new Tuple2(key, count));
		}

	}

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		Tuple3 emp1 = new Tuple3<Integer, String, String>(1, "Krishna", "Bangalore");
		Tuple3 emp2 = new Tuple3<Integer, String, String>(2, "Ram", "Hyderabad");
		Tuple3 emp3 = new Tuple3<Integer, String, String>(3, "Abhi", "Bangalore");
		Tuple3 emp4 = new Tuple3<Integer, String, String>(4, "Bala", "Hyderabad");
		Tuple3 emp5 = new Tuple3<Integer, String, String>(5, "Deeraj", "Hyderabad");
		Tuple3 emp6 = new Tuple3<Integer, String, String>(6, "Ramana", "Hyderabad");

		DataSet<Tuple3<Integer, String, String>> employees = executionEnvironment.fromElements(emp1, emp2, emp3, emp4,
				emp5, emp6);
		
		employees
		.groupBy(2)
		.combineGroup(new EmployeesCountByCityGroupCombinerFunction())
		.collect()
				.forEach(System.out::println);
	}

}