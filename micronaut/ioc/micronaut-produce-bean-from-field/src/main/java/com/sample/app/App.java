package com.sample.app;

import com.sample.app.model.SqlDataSource;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			SqlDataSource sqlDataSource1 = applicationContext.getBean(SqlDataSource.class,
					Qualifiers.byName("dataSource1"));
			SqlDataSource sqlDataSource2 = applicationContext.getBean(SqlDataSource.class,
					Qualifiers.byName("dataSource2"));

			System.out.println("sqlDataSource1 : " + sqlDataSource1);
			System.out.println("sqlDataSource2 : " + sqlDataSource2);
		}
	}
}