package com.sample.app.configs;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class AppConfig {

	@Value("${appName}")
	private String name;

	@Value("${appVersion}")
	private String version;

	@Value("${appLicence:apache}")
	private String licence;

	@Value("${appHome:`https://my-chat-server.com`}")
	private String home;

	@Value("${appName} can send and receive messages. Version is ${appVersion}")
	private String description;

	@Value("Hi there!!!!!")
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
