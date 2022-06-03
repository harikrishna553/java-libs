package com.sample.app.datasource.transformations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

import com.sample.app.dto.Customer;
import com.sample.app.dto.CustomerOrder;
import com.sample.app.dto.Order;

public class JoinTransformationWithJoinFunction {

	private static Date getDate(Integer year, Integer month, Integer day) throws ParseException {
		String dateStr = year + "-" + month + "-" + day;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(dateStr);
	}

	public static class CustomerOrdersJoiningFunction implements JoinFunction<Customer, Order, CustomerOrder> {

		@Override
		public CustomerOrder join(Customer customer, Order order) throws Exception {
			
			CustomerOrder customerOrder = new CustomerOrder();
			
			customerOrder.setCustomerId(customer.getId());
			customerOrder.setCustomerName(customer.getName());
			customerOrder.setOrderDate(order.getOrderDate());
			customerOrder.setOrderId(order.getId());
			
			return customerOrder;
		}

	}

	public static void main(String[] args) throws Exception {
		Customer customer1 = new Customer(1, "Lahari", "India");
		Customer customer2 = new Customer(2, "Sailu", "USA");
		Customer customer3 = new Customer(3, "Gopi", "Canada");
		Customer customer4 = new Customer(4, "Ram", "India");
		Customer customer5 = new Customer(5, "Raheem", "India");

		Order order1 = new Order(1, 3, getDate(2022, 5, 6));
		Order order2 = new Order(2, 2, getDate(2022, 5, 7));
		Order order3 = new Order(3, 1, getDate(2022, 5, 7));
		Order order4 = new Order(4, 3, getDate(2022, 5, 8));
		Order order5 = new Order(5, 4, getDate(2022, 5, 9));

		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSource<Customer> customersDataSet = executionEnvironment.fromElements(customer1, customer2, customer3,
				customer4, customer5);

		DataSource<Order> ordersDataSet = executionEnvironment.fromElements(order1, order2, order3, order4, order5);

		DataSet<CustomerOrder> resultDataSet;
		
		resultDataSet = customersDataSet
				.join(ordersDataSet)
				.where("id") // key of the first input dataset																			
				.equalTo("customerId") // key of the first input (orders)
				.with(new CustomerOrdersJoiningFunction());

		resultDataSet.collect()
		.forEach(System.out::println);

	}

}

