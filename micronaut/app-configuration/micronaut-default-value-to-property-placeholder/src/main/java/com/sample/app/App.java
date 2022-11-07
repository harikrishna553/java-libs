package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			Environment environment = applicationContext.getEnvironment();

			String appName = environment.getProperty("appName", String.class, "No appName found");
			String appVersion = environment.getProperty("appVersion", String.class, "No appVersion found");
			String appDescription = environment.getProperty("appDescription", String.class, "No description found");
			String appUrls = environment.getProperty("appUrls", String.class, "No appUrls found");

			System.out.println("appName : " + appName);
			System.out.println("appVersion : " + appVersion);
			System.out.println("appDescription : " + appDescription);
			System.out.println("appUrls : " + appUrls);
		}

	}
}
