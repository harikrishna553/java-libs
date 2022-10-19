package com.sample.app.datasources;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Requires(property = "datasource.url")
@Requires(property = "datasource.port")
@Requires(property = "datasource.username")
@Singleton
public class DataSource {

	@Value("${datasource.url}")
    private String dataSourceUrl;
	
	@Value("${datasource.port}")
    private String dataSourcePort;
	
	@Value("${datasource.username}")
    private String dataSourceUsername;

	public String getDataSourceUrl() {
		return dataSourceUrl;
	}

	public void setDataSourceUrl(String dataSourceUrl) {
		this.dataSourceUrl = dataSourceUrl;
	}

	public String getDataSourcePort() {
		return dataSourcePort;
	}

	public void setDataSourcePort(String dataSourcePort) {
		this.dataSourcePort = dataSourcePort;
	}

	public String getDataSourceUsername() {
		return dataSourceUsername;
	}

	public void setDataSourceUsername(String dataSourceUsername) {
		this.dataSourceUsername = dataSourceUsername;
	}
	
	
}
