package com.sample.app.configuration;

import java.util.Map;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.convert.format.MapFormat;
import io.micronaut.core.convert.format.MapFormat.MapTransformation;
import jakarta.inject.Singleton;

@Singleton
public class AppConfig {
	@Property(name = "spring.datasource")
	@MapFormat(transformation = MapTransformation.FLAT)
	protected Map<String, String> datasourceFlatMap;

	@Property(name = "spring.datasource")
	@MapFormat(transformation = MapTransformation.NESTED)
	protected Map<String, String> datasourceNestedMap;

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
