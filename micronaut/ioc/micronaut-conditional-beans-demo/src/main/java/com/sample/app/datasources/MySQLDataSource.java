package com.sample.app.datasources;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Singleton
@Requires(beans = DataSource.class)
@Requires(property = "datasource.url")
@Requires(property = "datasource.port")
@Requires(property = "datasource.username")
public class MySQLDataSource {

	private DataSource dataSource;

	public MySQLDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
