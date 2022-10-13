package com.sample.app.factories;

import com.sample.app.model.SqlDataSource;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;

@Factory
public class BeansFactory {

	@Bean
	@Named("dataSource1")
	SqlDataSource sqlDataSource1 = new SqlDataSource("abc.com", "Krishna");

	@Bean
	@Named("dataSource2")
	SqlDataSource sqlDataSource2 = new SqlDataSource("def.com", "Ram");
}