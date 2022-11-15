package com.sample.app;

import java.util.Collection;

import com.sample.app.configuration.DataSourceConfiguration;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			Collection<DataSourceConfiguration> configs = applicationContext
					.getBeansOfType(DataSourceConfiguration.class);

			for (DataSourceConfiguration config : configs) {
				System.out.println(config);
			}
		}

	}
}
