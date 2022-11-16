package com.sample.app.configuration;

import io.micronaut.context.annotation.ConfigurationInject;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.bind.annotation.Bindable;

@ConfigurationProperties("my.app")
public class AppConfig {
	private final String name;
	private final String version;
	private final String description;

	@ConfigurationInject
	public AppConfig(@NonNull String name, String version,
			@Bindable(defaultValue = "chat server send and receive messages") String description) {
		this.name = name;
		this.version = version;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getDescription() {
		return description;
	}

}
