package com.sample.app.datasource.transformations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.util.Collector;

public class CoGroupTransformationDemo {

	private static Date getDate(Integer year, Integer month, Integer day) throws ParseException {
		String dateStr = year + "-" + month + "-" + day;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(dateStr);
	}

	private static class CustomerOrdersCoGroupFunction implements
			CoGroupFunction<Tuple2<Integer, String>, Tuple3<Integer, Integer, Date>, Tuple4<Integer, String, Integer, Date>> {

		@Override
		public void coGroup(Iterable<Tuple2<Integer, String>> customers,
				Iterable<Tuple3<Integer, Integer, Date>> orders, Collector<Tuple4<Integer, String, Integer, Date>> out)
				throws Exception {

			Integer customerId = null;
			String customerName = null;

			for (Tuple2<Integer, String> customer : customers) {
				customerId = customer.f0;
				customerName = customer.f1;
			}

			for (Tuple3<Integer, Integer, Date> order : orders) {
				Integer orderId = order.f0;
				Date orderDate = order.f2;
				out.collect(new Tuple4<>(customerId, customerName, orderId, orderDate));
			}

		}

	}

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		Tuple2<Integer, String> customer1 = new Tuple2<>(1, "Ram");
		Tuple2<Integer, String> customer2 = new Tuple2<>(2, "Joel");
		Tuple2<Integer, String> customer3 = new Tuple2<>(3, "Sailu");
		Tuple2<Integer, String> customer4 = new Tuple2<>(4, "Gopi");

		Tuple3<Integer, Integer, Date> order1 = new Tuple3<>(1, 2, getDate(2022, 1, 20));
		Tuple3<Integer, Integer, Date> order2 = new Tuple3<>(2, 2, getDate(2022, 1, 21));
		Tuple3<Integer, Integer, Date> order3 = new Tuple3<>(3, 3, getDate(2022, 1, 21));
		Tuple3<Integer, Integer, Date> order4 = new Tuple3<>(4, 3, getDate(2022, 1, 22));
		Tuple3<Integer, Integer, Date> order5 = new Tuple3<>(5, 3, getDate(2022, 1, 22));
		Tuple3<Integer, Integer, Date> order6 = new Tuple3<>(6, 4, getDate(2022, 1, 22));
		Tuple3<Integer, Integer, Date> order7 = new Tuple3<>(7, 1, getDate(2022, 1, 25));

		DataSet<Tuple2<Integer, String>> customersDataSet = executionEnvironment.fromElements(customer1, customer2,
				customer3, customer4);
		DataSet<Tuple3<Integer, Integer, Date>> ordersDataSet = executionEnvironment.fromElements(order1, order2,
				order3, order4, order5, order6, order7);

		customersDataSet.coGroup(ordersDataSet)
		.where(0)
		.equalTo(1)
		.with(new CustomerOrdersCoGroupFunction())
		.collect()
		.forEach(System.out::println);

	}

}
