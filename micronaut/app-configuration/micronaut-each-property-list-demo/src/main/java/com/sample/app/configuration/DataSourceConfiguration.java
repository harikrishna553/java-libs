package com.sample.app.configuration;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.order.Ordered;

@EachProperty(value = "app.datasource", list = true)
public class DataSourceConfiguration implements Ordered{

	private Integer connectionTimeout;

	private Integer maxLifetime;

	private Integer maximumPoolSize;

	private String name;

	private Integer index;

	public DataSourceConfiguration(@Parameter Integer index) {
		this.index = index;
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "DataSourceConfiguration [connectionTimeout=" + connectionTimeout + ", maxLifetime=" + maxLifetime
				+ ", maximumPoolSize=" + maximumPoolSize + ", name=" + name + ", index=" + index + "]";
	}

}
