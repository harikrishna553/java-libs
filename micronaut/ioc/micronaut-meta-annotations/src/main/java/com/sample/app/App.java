package com.sample.app;

import com.sample.app.model.SqlDataSource;

import io.micronaut.context.ApplicationContext;;

public class App {
	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			SqlDataSource sqlDataSource = applicationContext.getBean(SqlDataSource.class);

			System.out.println("url : " + sqlDataSource.getDataSourceURL());
			System.out.println("username : " + sqlDataSource.getDataSourceUsername());
			System.out.println("port : " + sqlDataSource.getDataSourcePort());
		}
	}
}