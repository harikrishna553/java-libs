package com.sample.app.datasources;

import com.sample.app.annotations.RequiresDataSource;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Singleton
@Requires(beans = DataSource.class)
@RequiresDataSource
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
