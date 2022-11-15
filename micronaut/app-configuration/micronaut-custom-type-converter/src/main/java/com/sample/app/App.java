package com.sample.app;

import com.sample.app.configuration.AppConfig;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			AppConfig appConfig = applicationContext.getBean(AppConfig.class);

			System.out.println(appConfig.getEmp1());
			System.out.println(appConfig.getEmp2());
		}

	}
}
