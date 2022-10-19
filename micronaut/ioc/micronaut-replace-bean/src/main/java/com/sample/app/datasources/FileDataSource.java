package com.sample.app.datasources;

import io.micronaut.context.annotation.Replaces;
import jakarta.inject.Singleton;

@Singleton
@Replaces(MySQLDataSource.class)
public class FileDataSource implements DataSource{

	@Override
	public String toString() {
		return "FileDataSource";
	}

	
}
