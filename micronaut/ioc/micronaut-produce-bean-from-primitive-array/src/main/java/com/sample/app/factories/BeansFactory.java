package com.sample.app.factories;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;

@Factory
public class BeansFactory {

	@Bean
	@Named("primes")
	int[] primes = { 2, 3, 5, 7, 11 };

}