package com.sample.app.converter;

import java.time.Instant;
import java.util.Date;

import org.dozer.DozerConverter;

public class LogTimeConverter extends DozerConverter<Date, Long> {

	public LogTimeConverter() {
		super(Date.class, Long.class);
	}

	@Override
	public Long convertTo(Date source, Long destination) {
		return source.getTime();
	}

	@Override
	public Date convertFrom(Long source, Date destination) {
		return Date.from(Instant.ofEpochMilli(source));
	}

}
