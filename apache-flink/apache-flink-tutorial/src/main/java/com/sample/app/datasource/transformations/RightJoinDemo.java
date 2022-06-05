package com.sample.app.datasource.transformations;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

public class RightJoinDemo {

	private static class MyJoinFunction
			implements JoinFunction<Tuple2<String, Integer>, Tuple2<Integer, String>, Tuple3<Integer, String, String>> {

		@Override
		public Tuple3<Integer, String, String> join(Tuple2<String, Integer> order, Tuple2<Integer, String> product)
				throws Exception {

			Tuple3<Integer, String, String> tuple3 = new Tuple3<>();

			tuple3.f0 = product.f0;
			tuple3.f1 = product.f1;

			if (order != null)
				tuple3.f2 = order.f0;

			return tuple3;
		}

	}

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		// Store product Id and product name
		Tuple2<Integer, String> product1 = new Tuple2<>(1, "p1");
		Tuple2<Integer, String> product2 = new Tuple2<>(2, "p2");
		Tuple2<Integer, String> product3 = new Tuple2<>(3, "p3");
		Tuple2<Integer, String> product4 = new Tuple2<>(4, "p4");
		Tuple2<Integer, String> product5 = new Tuple2<>(5, "p5");

		// Store orderId and productId
		Tuple2<String, Integer> order1 = new Tuple2<>("o1", 1);
		Tuple2<String, Integer> order2 = new Tuple2<>("o2", 2);
		Tuple2<String, Integer> order3 = new Tuple2<>("o3", 4);
		Tuple2<String, Integer> order4 = new Tuple2<>("o4", 2);
		Tuple2<String, Integer> order5 = new Tuple2<>("o5", 1);

		DataSource<Tuple2<Integer, String>> productsDataSet = executionEnvironment.fromElements(product1, product2,
				product3, product4, product5);

		DataSource<Tuple2<String, Integer>> ordersDataSet = executionEnvironment.fromElements(order1, order2, order3,
				order4, order5);

		System.out.println("Result by joining the datasets");
		ordersDataSet
		.join(productsDataSet)
		.where(1)
		.equalTo(0)
		.with(new MyJoinFunction())
		.collect()
		.forEach(System.out::println);
		
		System.out.println("\n\nResult by right joining the datasets");
		ordersDataSet
		.rightOuterJoin(productsDataSet)
		.where(1)
		.equalTo(0)
		.with(new MyJoinFunction())
		.collect()
		.forEach(System.out::println);

	}

}

