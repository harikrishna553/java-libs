package com.sample.app;

import com.sample.app.datasources.MySQLDataSource;

import io.micronaut.context.ApplicationContext;;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			MySQLDataSource mySQLDataSource = applicationContext.getBean(MySQLDataSource.class);

			System.out.println("url : " + mySQLDataSource.getDataSource().getDataSourceUrl());
			System.out.println("port : " + mySQLDataSource.getDataSource().getDataSourcePort());
			System.out.println("username : " + mySQLDataSource.getDataSource().getDataSourceUsername());
		}
	}
}