package com.sample.app.configs;

import java.util.Map;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.convert.format.MapFormat;
import io.micronaut.core.convert.format.MapFormat.MapTransformation;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AppConfig {

	@Property(name = "app-name")
	protected String name;

	protected String version;

	@Property(name = "spring.datasource.communicationtimeout")
	protected Integer communicationTimeout;

	@Property(name = "spring.datasource")
	@MapFormat(transformation = MapTransformation.FLAT)
	protected Map<String, String> datasourceFlatMap;

	@Property(name = "spring.datasource")
	@MapFormat
	protected Map<String, String> datasourceNestedMap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	@Inject
	public void setVersion(@Property(name = "appVersion") String version) {
		this.version = version;
	}

	public Integer getCommunicationTimeout() {
		return communicationTimeout;
	}

	public void setCommunicationTimeout(Integer communicationTimeout) {
		this.communicationTimeout = communicationTimeout;
	}

	public Map<String, String> getDatasourceFlatMap() {
		return datasourceFlatMap;
	}

	public void setDatasourceFlatMap(Map<String, String> datasourceFlatMap) {
		this.datasourceFlatMap = datasourceFlatMap;
	}

	public Map<String, String> getDatasourceNestedMap() {
		return datasourceNestedMap;
	}

	public void setDatasourceNestedMap(Map<String, String> datasourceNestedMap) {
		this.datasourceNestedMap = datasourceNestedMap;
	}

}
