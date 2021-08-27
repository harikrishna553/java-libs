package com.sample.app.dto;

import java.util.Map;

public class SqlQueryRequestDto {

	private Map<String, Object> transformations;
	private String tableName;

	public Map<String, Object> getTransformations() {
		return transformations;
	}

	public void setTransformations(Map<String, Object> transformations) {
		this.transformations = transformations;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
