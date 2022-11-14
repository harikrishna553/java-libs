package com.sample.app.configuration;

import java.time.LocalDate;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.convert.format.Format;

@ConfigurationProperties("my.app")
public class AppConfig {

	@Format("yyyy-MM-dd") 
	private LocalDate publishedDate;

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

}
