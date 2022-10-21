package com.sample.app;

import io.micronaut.context.ApplicationContext;
import com.sample.app.model.Connection;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			Connection connection = applicationContext.getBean(Connection.class);

			System.out.println(connection);
		}

	}
}