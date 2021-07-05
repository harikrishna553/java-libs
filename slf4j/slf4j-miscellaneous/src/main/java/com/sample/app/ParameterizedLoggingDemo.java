package com.sample.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.app.model.Employee;

public class ParameterizedLoggingDemo {

	public static void main(String args[]) {
		Logger logger = LoggerFactory.getLogger(ParameterizedLoggingDemo.class);
		
		Employee emp1 = new Employee(123, "Krishna");
		Date date = new Date();

		logger.info("Employee {} logged in at {}", emp1, date);

	}

}
