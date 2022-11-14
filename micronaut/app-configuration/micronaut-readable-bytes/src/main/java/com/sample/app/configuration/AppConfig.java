package com.sample.app.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.convert.format.ReadableBytes;

@ConfigurationProperties("my.app")
public class AppConfig {

	private Integer tenMB;

	private Integer tenKB;

	private Long tenGB;

	public Integer getTenMB() {
		return tenMB;
	}

	public void setTenMB(@ReadableBytes Integer tenMB) {
		this.tenMB = tenMB;
	}

	public Integer getTenKB() {
		return tenKB;
	}

	public void setTenKB(@ReadableBytes Integer tenKB) {
		this.tenKB = tenKB;
	}

	public Long getTenGB() {
		return tenGB;
	}

	public void setTenGB(@ReadableBytes Long tenGB) {
		this.tenGB = tenGB;
	}

}
