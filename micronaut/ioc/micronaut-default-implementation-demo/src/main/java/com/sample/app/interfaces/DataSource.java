package com.sample.app.interfaces;

import io.micronaut.context.annotation.DefaultImplementation;
import jakarta.inject.Singleton;

@DefaultImplementation(FileDataSource.class)
public interface DataSource {

}

@Singleton
class FileDataSource implements DataSource {
	public String toString() {
		return "FileDataSource";
	}

}