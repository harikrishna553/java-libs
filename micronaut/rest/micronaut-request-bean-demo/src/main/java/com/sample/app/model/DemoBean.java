package com.sample.app.model;

import javax.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;

@Introspected
public class DemoBean {

	private HttpRequest<?> httpRequest;

	@PathVariable("entity-name")
	private String entityName;

	@Nullable
	@QueryValue
	@NotBlank
	private String country;

	@Nullable
	@QueryValue
	@NotBlank
	private String state;

	public DemoBean(HttpRequest<?> httpRequest, String entityName, @NotBlank String country, @NotBlank String state) {
		this.httpRequest = httpRequest;
		this.entityName = entityName;
		this.country = country;
		this.state = state;
	}

	public HttpRequest<?> getHttpRequest() {
		return httpRequest;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

}
