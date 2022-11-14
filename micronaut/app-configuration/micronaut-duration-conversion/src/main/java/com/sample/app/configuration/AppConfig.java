package com.sample.app.configuration;

import java.time.Duration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("my.app")
public class AppConfig {

	private Duration sleepTime;
	private Duration connectionTimeout;

	public Duration getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(Duration sleepTime) {
		this.sleepTime = sleepTime;
	}

	public Duration getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Duration connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

}
