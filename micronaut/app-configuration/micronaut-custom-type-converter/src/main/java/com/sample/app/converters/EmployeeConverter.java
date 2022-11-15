package com.sample.app.converters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.app.model.*;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

@Singleton
public class EmployeeConverter implements TypeConverter<String, Employee> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeConverter.class);

	@Override
	public Optional<Employee> convert(String str, Class<Employee> targetType, ConversionContext context) {
		try {
			return Optional.of(Employee.toEmployee(str));
		} catch (Exception e) {
			context.reject(str, e);
			LOGGER.error("{}", e);
			return Optional.empty();
		}
	}

}
