package com.sample.app;

import com.sample.app.beans.Connection;
import com.sample.app.beans.DataSource;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			applicationContext.getBean(DataSource.class);
			
			applicationContext.getBean(Connection.class, Qualifiers.byName("my_connection1"));
			
			applicationContext.getBean(Connection.class, Qualifiers.byName("my_connection2"));
		}

		System.out.println("about to terminate the app");
	}
}