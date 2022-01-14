package com.sample.app.converters;

import java.sql.Array;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.sample.app.model.JdbcColumnMetadataDto;
import com.sample.app.model.JdbcDatabaseMetadataDto;
import com.sample.app.model.JdbcForeignKeyDto;
import com.sample.app.model.JdbcTableMetadataDto;

public class DatabaseMetaDataConverter {
	private JdbcDatabaseMetadataDto dto = new JdbcDatabaseMetadataDto();
	private DatabaseMetaData databaseMetaData;

	public DatabaseMetaDataConverter(DatabaseMetaData databaseMetaData) {
		this.databaseMetaData = databaseMetaData;
	}

	public JdbcDatabaseMetadataDto convert() throws SQLException {
		populateBasicInfo();
		populateTablesMetadata();
		return dto;
	}

	private void populateTablesMetadata() throws SQLException {

		String table[] = { "TABLE" };
		try (ResultSet resultSet = databaseMetaData.getTables(null, null, null, table)) {
			List<String> tableNames = new ArrayList<>();
			Map<String, JdbcTableMetadataDto> tablesMetadata = new HashMap<>();

			while (resultSet.next()) {
				String catalog = resultSet.getString("TABLE_CAT");
				String schema = resultSet.getString("TABLE_SCHEM");
				String tableName = resultSet.getString("TABLE_NAME");

				tableNames.add(tableName);

				JdbcTableMetadataDto tableMetadata = getTableMetadata(tableName);
				tablesMetadata.put(tableName, tableMetadata);

				List<String> primaryKeys = new ArrayList<>();

				// Set primary keys
				try (ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName)) {
					while (primaryKeysResultSet.next()) {
						primaryKeys.add(primaryKeysResultSet.getString("COLUMN_NAME"));
					}
				}
				tableMetadata.setPrimaryKeys(primaryKeys);

				// Set foreign keys
				List<JdbcForeignKeyDto> foreignKeys = getForeignKeys(tableName);
				tableMetadata.setForeignKeys(foreignKeys);

				// Set indexes
				Map<String, List<String>> indexesInfo = getIndexsInfo(tableName);
				tableMetadata.setIndexes(indexesInfo);

			}
			dto.setTableNames(tableNames);
			dto.setTablesMetadata(tablesMetadata);
		}

	}

	private JdbcTableMetadataDto getTableMetadata(String tableName) throws SQLException {
		JdbcTableMetadataDto tableMetaData = new JdbcTableMetadataDto();
		tableMetaData.setTableName(tableName);
		Map<String, JdbcColumnMetadataDto> columnsMetaData = new HashMap<>();

		try (ResultSet resultSet = databaseMetaData.getColumns(null, null, tableName, null)) {
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				String typeName = resultSet.getString("TYPE_NAME");
				Integer columnSize = resultSet.getInt("COLUMN_SIZE");
				String isNullable = resultSet.getString("IS_NULLABLE");
				Integer decimalDigits = resultSet.getInt("DECIMAL_DIGITS");

				JdbcColumnMetadataDto columnDto = new JdbcColumnMetadataDto(columnName, typeName, columnSize,
						isNullable, decimalDigits);

				columnsMetaData.put(columnName, columnDto);
			}

			tableMetaData.setColumnsMetadata(columnsMetaData);
			return tableMetaData;
		}

	}

	public void populateBasicInfo() throws SQLException {
		String userName = databaseMetaData.getUserName();
		String connectionURL = databaseMetaData.getURL();
		String driverName = databaseMetaData.getDriverName();
		String driverVersion = databaseMetaData.getDriverVersion();

		dto.setUserName(userName);
		dto.setConnectionURL(connectionURL);
		dto.setDriverName(driverName);
		dto.setDriverVersion(driverVersion);
	}

	private List<JdbcForeignKeyDto> getForeignKeys(String tableName) throws SQLException {
		List<JdbcForeignKeyDto> foreignKeys = new ArrayList<>();

		try (ResultSet resultSet = databaseMetaData.getImportedKeys(null, null, tableName)) {
			while (resultSet.next()) {

				String foreignKeyColumnName = resultSet.getString("FKCOLUMN_NAME");
				String primaryKeyTableName = resultSet.getString("PKTABLE_NAME");
				String primaryKeyColumnName = resultSet.getString("PKCOLUMN_NAME");

				JdbcForeignKeyDto foreignKeyDto = new JdbcForeignKeyDto(foreignKeyColumnName, primaryKeyTableName,
						primaryKeyColumnName);
				foreignKeys.add(foreignKeyDto);
			}
		}

		return foreignKeys;
	}

	private Map<String, List<String>> getIndexsInfo(String tableName) throws SQLException {
		try (ResultSet resultSet = databaseMetaData.getIndexInfo(null, null, tableName, false, false)) {

			Map<String, List<String>> indexes = new HashMap<>();

			while (resultSet.next()) {
				String indexName = resultSet.getString("INDEX_NAME");
				String columnName = resultSet.getString("COLUMN_NAME");

				if (indexes.containsKey(indexName)) {
					indexes.get(indexName).add(columnName);
				} else {
					List<String> columnNames = new ArrayList<>();
					columnNames.add(columnName);
					indexes.put(indexName, columnNames);
				}
			}

			return indexes;
		}
	}

}
