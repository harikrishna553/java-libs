package com.sample.app;

import com.sample.app.configuration.AppConfig;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			AppConfig appConfig = applicationContext.getBean(AppConfig.class);

			System.out.println("10kb = " + appConfig.getTenKB() + " bytes");
			System.out.println("10mb = " + appConfig.getTenMB() + " bytes");
			System.out.println("10gb = " + appConfig.getTenGB() + " bytes");

		}

	}
}
