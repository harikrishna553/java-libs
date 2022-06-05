package com.sample.app.datasource.transformations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;

public class JoinWithDataSetSizeHint {

	private static Date getDate(Integer year, Integer month, Integer day) throws ParseException {
		String dateStr = year + "-" + month + "-" + day;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(dateStr);
	}

	public static void main(String[] args) throws Exception {

		Tuple3 customer1 = new Tuple3<Integer, String, String>(1, "Lahari", "India");
		Tuple3 customer2 = new Tuple3<Integer, String, String>(2, "Sailu", "USA");
		Tuple3 customer3 = new Tuple3<Integer, String, String>(3, "Gopi", "Canada");
		Tuple3 customer4 = new Tuple3<Integer, String, String>(4, "Ram", "India");
		Tuple3 customer5 = new Tuple3<Integer, String, String>(5, "Raheem", "India");
		Tuple3 customer6 = new Tuple3<Integer, String, String>(6, "Sudhir Sami", "India");
		Tuple3 customer7 = new Tuple3<Integer, String, String>(7, "Ravindra", "India");

		Tuple3 order1 = new Tuple3<Integer, Integer, Date>(1, 3, getDate(2022, 5, 6));
		Tuple3 order2 = new Tuple3<Integer, Integer, Date>(2, 2, getDate(2022, 5, 7));

		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSource<Tuple3<Integer, String, String>> customersDataSet = executionEnvironment.fromElements(customer1,
				customer2, customer3, customer4, customer5, customer6, customer7);

		DataSource<Tuple3<Integer, Integer, Date>> ordersDataSet = executionEnvironment.fromElements(order1, order2);

		DataSet<Tuple4<Integer, String, String, Date>> resultDataSet = null;
		resultDataSet = customersDataSet
				.joinWithTiny(ordersDataSet)
				.where(0) // key of the first input (customers)
				.equalTo(1) // key of the second input (orders)
				.projectFirst(0, 1).projectSecond(0, 2);

		resultDataSet.collect().forEach(tuple4 -> {
			System.out.println(tuple4);
		});

	}

}
