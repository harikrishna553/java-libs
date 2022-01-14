package com.sample.app.connection;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import com.sample.app.converters.DatabaseMetaDataConverter;
import com.sample.app.model.DatabaseMetadataDto;

public abstract class AbstractConnection implements Closeable {
	protected String databaseUrl;
	protected String user;
	protected String password;

	protected Connection connection;

	public AbstractConnection(String databaseUrl, String user, String password) throws SQLException {
		this.databaseUrl = databaseUrl;
		this.user = user;
		this.password = password;
		this.initConnection();
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public abstract void initConnection() throws SQLException;

	public DatabaseMetadataDto getMetadata() throws SQLException {
		DatabaseMetaData DatabaseMetaData = this.connection.getMetaData();

		DatabaseMetaDataConverter databaseMetadataConverter = new DatabaseMetaDataConverter(DatabaseMetaData);
		return databaseMetadataConverter.convert();
	}

	public void close() throws IOException {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				throw new IOException(e);
			}
		}
	}
}
