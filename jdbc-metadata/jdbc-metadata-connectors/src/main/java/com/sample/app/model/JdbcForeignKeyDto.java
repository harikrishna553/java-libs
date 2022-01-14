package com.sample.app.model;

public class JdbcForeignKeyDto {

	private String foreignKeyColumnName;
	private String primaryKeyTableName;
	private String primaryKeyColumnName;
	
	
	public JdbcForeignKeyDto() {}
	
	public JdbcForeignKeyDto(String foreignKeyColumnName, String primaryKeyTableName, String primaryKeyColumnName) {
		super();
		this.foreignKeyColumnName = foreignKeyColumnName;
		this.primaryKeyTableName = primaryKeyTableName;
		this.primaryKeyColumnName = primaryKeyColumnName;
	}

	public String getForeignKeyColumnName() {
		return foreignKeyColumnName;
	}

	public void setForeignKeyColumnName(String foreignKeyColumnName) {
		this.foreignKeyColumnName = foreignKeyColumnName;
	}

	public String getPrimaryKeyTableName() {
		return primaryKeyTableName;
	}

	public void setPrimaryKeyTableName(String primaryKeyTableName) {
		this.primaryKeyTableName = primaryKeyTableName;
	}

	public String getPrimaryKeyColumnName() {
		return primaryKeyColumnName;
	}

	public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
		this.primaryKeyColumnName = primaryKeyColumnName;
	}

}
