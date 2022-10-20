package com.sample.app;

import com.sample.app.interfaces.DataSource;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			DataSource dataSource = applicationContext.getBean(DataSource.class);
			
			System.out.println(dataSource);
		}
	}
}