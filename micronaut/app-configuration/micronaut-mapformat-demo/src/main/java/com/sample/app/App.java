package com.sample.app;

import java.util.Map;

import com.sample.app.configuration.AppConfig;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			AppConfig appConfig = applicationContext.getBean(AppConfig.class);

			System.out.println("\nFlat map informaiton");
			Map<String, String> datasourceProperties = appConfig.getDatasourceFlatMap();
			for (String key : datasourceProperties.keySet()) {
				System.out.println(key + " -> " + datasourceProperties.get(key));
			}

			System.out.println("\nNested map informaiton");
			datasourceProperties = appConfig.getDatasourceNestedMap();
			for (String key : datasourceProperties.keySet()) {
				System.out.println(key + " -> " + datasourceProperties.get(key));
			}
		}

	}
}
