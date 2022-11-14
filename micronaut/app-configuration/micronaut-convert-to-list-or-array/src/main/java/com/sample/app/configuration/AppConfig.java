package com.sample.app.configuration;

import java.util.List;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("my.app")
public class AppConfig {

	private List<Integer> primes;

	public List<Integer> getPrimes() {
		return primes;
	}

	public void setPrimes(List<Integer> primes) {
		this.primes = primes;
	}

}
