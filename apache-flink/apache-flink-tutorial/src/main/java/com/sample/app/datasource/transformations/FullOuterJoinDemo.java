package com.sample.app.datasource.transformations;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;


public class FullOuterJoinDemo {

	private static class ProductOrder{
		private Integer prodcutId;
		private String productName;
		private String orderId;
		
		@Override
		public String toString() {
			return "ProductOrder [prodcutId=" + prodcutId + ", productName=" + productName + ", orderId=" + orderId
					+ "]";
		}
		
		
	}
	private static class MyJoinFunction
			implements JoinFunction<Tuple2<String, Integer>, Tuple2<Integer, String>, ProductOrder> {

		@Override
		public ProductOrder join(Tuple2<String, Integer> order, Tuple2<Integer, String> product)
				throws Exception {

			ProductOrder productOrder = new ProductOrder();

			if(product != null) {
				productOrder.prodcutId = product.f0;
				productOrder.productName = product.f1;
			}
			
			if (order != null)
				productOrder.orderId = order.f0;

			return productOrder;
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
		Tuple2<String, Integer> order6 = new Tuple2<>("o6", 10);
		Tuple2<String, Integer> order7 = new Tuple2<>("o7", 11);

		DataSource<Tuple2<Integer, String>> productsDataSet = executionEnvironment.fromElements(product1, product2,
				product3, product4, product5);

		DataSource<Tuple2<String, Integer>> ordersDataSet = executionEnvironment.fromElements(order1, order2, order3,
				order4, order5, order6, order7);

		System.out.println("Result by joining the datasets");
		ordersDataSet
		.join(productsDataSet)
		.where(1)
		.equalTo(0)
		.with(new MyJoinFunction())
		.collect()
		.forEach(System.out::println);
		
		System.out.println("\n\nResult by full outer joining the datasets");
		ordersDataSet
		.fullOuterJoin(productsDataSet)
		.where(1)
		.equalTo(0)
		.with(new MyJoinFunction())
		.collect()
		.forEach(System.out::println);

	}

}

