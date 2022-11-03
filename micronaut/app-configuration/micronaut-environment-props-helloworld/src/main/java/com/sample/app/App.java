package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			Environment environment = applicationContext.getEnvironment();
			
			String appName = environment.getProperty("appName", String.class, "demo chat server");
			String version = environment.getProperty("version", String.class, "1.0");
			String orgName = environment.getProperty("organization", String.class, "ABC Demo Corp");
			
			System.out.println("appName : " + appName);
			System.out.println("version : " + version);
			System.out.println("orgName : " + orgName);
		}

	}
}
