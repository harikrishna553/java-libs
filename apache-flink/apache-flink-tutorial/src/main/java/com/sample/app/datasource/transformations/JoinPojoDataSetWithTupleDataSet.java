package com.sample.app.datasource.transformations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import com.sample.app.dto.Customer;

public class JoinPojoDataSetWithTupleDataSet {

	private static Date getDate(Integer year, Integer month, Integer day) throws ParseException {
		String dateStr = year + "-" + month + "-" + day;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(dateStr);
	}

	public static void main(String[] args) throws Exception {
		Customer customer1 = new Customer(1, "Lahari", "India");
		Customer customer2 = new Customer(2, "Sailu", "USA");
		Customer customer3 = new Customer(3, "Gopi", "Canada");
		Customer customer4 = new Customer(4, "Ram", "India");
		Customer customer5 = new Customer(5, "Raheem", "India");
		
		Tuple3<Integer, Integer, Date> order1 = new Tuple3<Integer, Integer, Date> (1, 3, getDate(2022, 5, 6));
		Tuple3<Integer, Integer, Date> order2 = new Tuple3<Integer, Integer, Date> (2, 2, getDate(2022, 5, 7));
		Tuple3<Integer, Integer, Date> order3 = new Tuple3<Integer, Integer, Date> (3, 1, getDate(2022, 5, 7));
		Tuple3<Integer, Integer, Date> order4 = new Tuple3<Integer, Integer, Date> (4, 3, getDate(2022, 5, 8));
		Tuple3<Integer, Integer, Date> order5 = new Tuple3<Integer, Integer, Date> (5, 4, getDate(2022, 5, 9));

		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSource<Customer> customersDataSet = executionEnvironment.fromElements(customer1, customer2, customer3,
				customer4, customer5);

		DataSource<Tuple3<Integer, Integer, Date>> ordersDataSet = executionEnvironment.fromElements(order1, order2, 
				order3, order4, order5);
		
		DataSet<Tuple2<Customer, Tuple3<Integer, Integer, Date>>> resultDataSet = customersDataSet.join(ordersDataSet)
		.where("id") // key of the first input (customers)
		.equalTo("f1"); // key of the second input (orders)
		
		resultDataSet.collect()
		.forEach(tuple2 -> {
			Customer customer = tuple2.f0;
			Tuple3<Integer, Integer, Date> order = tuple2.f1;
			
			System.out.println(customer.getId() + "," +customer.getName() + "," + customer.getCountry() + "," + order.f0 + ","+order.f2);
		});
		

	}

}

