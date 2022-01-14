package com.sample.app.model;

import java.util.List;
import java.util.Map;

public class JdbcTableMetadataDto {

	private String tableName;
	private Map<String, JdbcColumnMetadataDto> columnsMetadata;
	private List<String> primaryKeys;
	private List<JdbcForeignKeyDto> foreignKeys;
	private Map<String, List<String>> indexes;

	public Map<String, JdbcColumnMetadataDto> getColumnsMetadata() {
		return columnsMetadata;
	}

	public void setColumnsMetadata(Map<String, JdbcColumnMetadataDto> columnsMetadata) {
		this.columnsMetadata = columnsMetadata;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public List<JdbcForeignKeyDto> getForeignKeys() {
		return foreignKeys;
	}

	public void setForeignKeys(List<JdbcForeignKeyDto> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	public Map<String, List<String>> getIndexes() {
		return indexes;
	}

	public void setIndexes(Map<String, List<String>> indexes) {
		this.indexes = indexes;
	}

}
