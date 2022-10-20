package com.sample.app.interfaces.impl;

import com.sample.app.interfaces.DataSource;

import io.micronaut.context.annotation.Replaces;
import jakarta.inject.Singleton;

@Singleton
@Replaces(DataSource.class)
public class MySQLDataSource implements DataSource {

	@Override
	public String toString() {
		return "MySQLDataSource";
	}

}
