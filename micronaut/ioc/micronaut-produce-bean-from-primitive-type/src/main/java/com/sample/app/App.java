package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			Double pi = applicationContext.getBean(Double.class,
					Qualifiers.byName("pi"));
			String appName = applicationContext.getBean(String.class,
					Qualifiers.byName("appName"));

			System.out.println("pi : " + pi);
			System.out.println("appName : " + appName);
		}
	}
}