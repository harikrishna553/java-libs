package com.sample.app.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.bind.annotation.Bindable;

import jakarta.annotation.Nonnull;

@ConfigurationProperties("my.app")
public interface AppConfig {

	@Bindable(defaultValue = "my chat server")
	@Nonnull
	String getName();

	@Bindable(defaultValue = "1.0.0")
	String getVersion();

	@Bindable(defaultValue = "chat server send and receive messages")
	String getDescription();

}
