package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			Environment environment = applicationContext.getEnvironment();

			String a1 = environment.getProperty("a1", String.class, "a1");
			String a2 = environment.getProperty("a2", String.class, "a2");
			String a3 = environment.getProperty("a3", String.class, "a3");

			System.out.println("a1 : " + a1);
			System.out.println("a2 : " + a2);
			System.out.println("a3 : " + a3);
		}

	}
}
