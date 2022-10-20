package com.sample.app.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;

@Singleton
public class DataSource {

	public DataSource() {
		System.out.println("\nDatasource constructor called");
	}

	@PreDestroy
	public void close() {
		System.out.println("DataSource closed");
	}

	@PostConstruct
	public void initialize() {
		System.out.println("DataSource initialized");
	}
}
