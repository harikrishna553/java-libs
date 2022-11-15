package com.sample.app.datasources;

import com.sample.app.configuration.DataSourceConfiguration;

public class SqlDatasource {

	private DataSourceConfiguration dataSourceConfiguration;

	public SqlDatasource(DataSourceConfiguration dataSourceConfiguration) {
		this.dataSourceConfiguration = dataSourceConfiguration;
	}

	@Override
	public String toString() {
		return "SqlDatasource [dataSourceConfiguration=" + dataSourceConfiguration + "]";
	}

}
