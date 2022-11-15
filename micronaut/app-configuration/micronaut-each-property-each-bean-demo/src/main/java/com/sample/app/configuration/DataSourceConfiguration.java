package com.sample.app.configuration;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty(value = "app.datasource")
public class DataSourceConfiguration {

	private Integer connectionTimeout;

	private Integer maxLifetime;

	private Integer maximumPoolSize;

	private String name;

	private String url;

	public DataSourceConfiguration(@Parameter String name) {
		this.name = name;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(Integer maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public Integer getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(Integer maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "DataSourceConfiguration [connectionTimeout=" + connectionTimeout + ", maxLifetime=" + maxLifetime
				+ ", maximumPoolSize=" + maximumPoolSize + ", name=" + name + ", url=" + url + "]";
	}

}
