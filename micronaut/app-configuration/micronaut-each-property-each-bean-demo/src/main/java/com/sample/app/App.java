package com.sample.app;

import java.util.Collection;

import com.sample.app.datasources.SqlDatasource;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			SqlDatasource devSource = applicationContext.getBean(SqlDatasource.class, Qualifiers.byName("dev-source"));
			SqlDatasource qaSource = applicationContext.getBean(SqlDatasource.class, Qualifiers.byName("dev-source"));
			SqlDatasource prodSource = applicationContext.getBean(SqlDatasource.class, Qualifiers.byName("dev-source"));

			System.out.println(devSource);
			System.out.println(qaSource);
			System.out.println(prodSource + "\n");

			Collection<SqlDatasource> datasources = applicationContext.getBeansOfType(SqlDatasource.class);

			for (SqlDatasource datasource : datasources) {
				System.out.println(datasource);
			}
		}

	}
}
