package com.sample.app.model;

import com.sample.app.annotations.DataSource;

import io.micronaut.context.annotation.Value;

@DataSource
public class SqlDataSource {
	@Value("${datasource.url}")
	private String dataSourceURL;

	@Value("${datasource.port}")
	private String dataSourcePort;

	@Value("${datasource.username}")
	private String dataSourceUsername;

	public String getDataSourceURL() {
		return dataSourceURL;
	}

	public void setDataSourceURL(String dataSourceURL) {
		this.dataSourceURL = dataSourceURL;
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
