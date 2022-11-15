package com.sample.app.factory;

import com.sample.app.configuration.DataSourceConfiguration;
import com.sample.app.datasources.SqlDatasource;

import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;

@Factory
public class DatasourceFactory {

	@EachBean(DataSourceConfiguration.class)
	SqlDatasource dataSource(DataSourceConfiguration configuration) {
		return new SqlDatasource(configuration);
	}
}
