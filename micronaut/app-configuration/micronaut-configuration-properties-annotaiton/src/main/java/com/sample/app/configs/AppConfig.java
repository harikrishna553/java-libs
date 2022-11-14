package com.sample.app.configs;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.annotation.Nonnull;

@ConfigurationProperties("my.app")
public class AppConfig {

	private String name = "my chat server";

	private String description;

	@Nonnull
	private String version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
