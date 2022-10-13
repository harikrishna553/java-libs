package com.sample.app.factories;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;

@Factory
public class BeansFactory {

	@Bean
	@Named("pi")
	Double PI = 3.14;

	@Bean
	@Named("appName")
	String appName = "chat server";
}