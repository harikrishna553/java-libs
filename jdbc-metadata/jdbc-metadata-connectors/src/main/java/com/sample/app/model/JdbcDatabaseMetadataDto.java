package com.sample.app.model;

import java.util.List;
import java.util.Map;

public class JdbcDatabaseMetadataDto extends DatabaseMetadataDto {

	private List<String> tableNames;
	private Map<String, JdbcTableMetadataDto> tablesMetadata;

	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public Map<String, JdbcTableMetadataDto> getTablesMetadata() {
		return tablesMetadata;
	}

	public void setTablesMetadata(Map<String, JdbcTableMetadataDto> tablesMetadata) {
		this.tablesMetadata = tablesMetadata;
	}

}
