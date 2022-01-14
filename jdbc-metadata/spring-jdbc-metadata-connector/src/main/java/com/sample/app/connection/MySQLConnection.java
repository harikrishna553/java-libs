package com.sample.app.connection;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

public class MySQLConnection extends AbstractConnection {

	public MySQLConnection(String databaseUrl, String user, String password) throws SQLException {
		super(databaseUrl, user, password);
	}

	public void initConnection() throws SQLException {
		DataSource datasource = DataSourceBuilder.create().url(databaseUrl).username(user).password(password)
				.driverClassName("com.mysql.cj.jdbc.Driver").build();
		this.connection = datasource.getConnection();
	}
}