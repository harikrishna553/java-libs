package com.sample.app;

import com.sample.app.configs.AppConfig;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			AppConfig appConfig = applicationContext.getBean(AppConfig.class);

			String appName = appConfig.getName();
			String version = appConfig.getVersion();
			String description = appConfig.getDescription();

			System.out.println("appName : " + appName);
			System.out.println("version : " + version);
			System.out.println("description : " + description + "\n");

		}

	}
}