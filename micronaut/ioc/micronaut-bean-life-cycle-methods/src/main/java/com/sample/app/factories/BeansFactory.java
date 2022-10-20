package com.sample.app.factories;

import com.sample.app.beans.Connection;

import io.micronaut.context.annotation.Bean;

import jakarta.inject.Named;
import jakarta.inject.Singleton;
import io.micronaut.context.annotation.Factory;

@Factory
public class BeansFactory {

	@Singleton
	@Bean(preDestroy = "stop")
	@Named("my_connection1")
	public Connection connection1() {
		return new Connection();
	}

	@Singleton
	@Bean(preDestroy = "destroy")
	@Named("my_connection2")
	public Connection connection2() {
		return new Connection();
	}
}
