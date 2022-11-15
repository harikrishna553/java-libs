package com.sample.app.configuration;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("app.datasource")
public class DataSourceConfiguration {

	private Integer connectionTimeout;

	private Integer maxLifetime;

	private Integer maximumPoolSize;

	private String name;

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

	@Override
	public String toString() {
		return "DataSourceConfiguration [connectionTimeout=" + connectionTimeout + ", maxLifetime=" + maxLifetime
				+ ", maximumPoolSize=" + maximumPoolSize + ", name=" + name + "]";
	}

}
