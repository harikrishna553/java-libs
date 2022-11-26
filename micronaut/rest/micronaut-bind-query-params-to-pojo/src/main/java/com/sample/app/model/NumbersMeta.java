package com.sample.app.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class NumbersMeta {
	private Integer from;

	private Integer to;

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

}
