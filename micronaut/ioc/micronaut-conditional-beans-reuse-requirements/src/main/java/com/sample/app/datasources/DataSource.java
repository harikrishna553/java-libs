package com.sample.app.datasources;

import com.sample.app.annotations.RequiresDataSource;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
@RequiresDataSource
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
