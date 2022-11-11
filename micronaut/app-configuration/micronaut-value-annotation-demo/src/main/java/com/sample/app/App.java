package com.sample.app;

import com.sample.app.configs.AppConfig;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			AppConfig appConfig = applicationContext.getBean(AppConfig.class);

			String appName = appConfig.getName();
			String version = appConfig.getVersion();
			String licence = appConfig.getLicence();
			String home = appConfig.getHome();
			String description = appConfig.getDescription();
			String message = appConfig.getMessage();

			System.out.println("appName : " + appName);
			System.out.println("version : " + version);
			System.out.println("licence : " + licence);
			System.out.println("home : " + home);
			System.out.println("description : " + description);
			System.out.println("message : " + message);
		}

	}
}