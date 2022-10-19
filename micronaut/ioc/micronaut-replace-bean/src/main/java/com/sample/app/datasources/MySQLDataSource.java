package com.sample.app.datasources;

import jakarta.inject.Singleton;

@Singleton
public class MySQLDataSource implements DataSource {

	@Override
	public String toString() {
		return "MySQLDataSource";
	}

}
