package com.sample.app.model;

public class JdbcColumnMetadataDto {
	private String columnName;
	private String typeName;
	private Integer columnSize;
	private String isNullbale;
	private Integer decimalDigits;

	public JdbcColumnMetadataDto() {

	}

	public JdbcColumnMetadataDto(String columnName, String typeName, Integer columnSize, String isNullbale, Integer decimalDigits) {
		super();
		this.columnName = columnName;
		this.typeName = typeName;
		this.columnSize = columnSize;
		this.isNullbale = isNullbale;
		this.decimalDigits = decimalDigits;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(Integer columnSize) {
		this.columnSize = columnSize;
	}

	public String getIsNullbale() {
		return isNullbale;
	}

	public void setIsNullbale(String isNullbale) {
		this.isNullbale = isNullbale;
	}

}
