package com.sample.app;

import java.util.Collection;

import com.sample.app.configuration.DataSourceConfiguration;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			DataSourceConfiguration devSource = applicationContext.getBean(DataSourceConfiguration.class,
					Qualifiers.byName("dev-source"));
			DataSourceConfiguration qaSource = applicationContext.getBean(DataSourceConfiguration.class,
					Qualifiers.byName("qa-source"));
			DataSourceConfiguration prodSource = applicationContext.getBean(DataSourceConfiguration.class,
					Qualifiers.byName("prod-source"));

			System.out.println(devSource);
			System.out.println(qaSource);
			System.out.println(prodSource +"\n");

			// Get all beans of given type
			Collection<DataSourceConfiguration> configs = applicationContext
					.getBeansOfType(DataSourceConfiguration.class);

			for (DataSourceConfiguration config : configs) {
				System.out.println(config);
			}
		}

	}
}

