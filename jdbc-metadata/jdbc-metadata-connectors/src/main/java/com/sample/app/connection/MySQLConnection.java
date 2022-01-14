package com.sample.app.connection;

import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver;

public class MySQLConnection extends AbstractConnection {

	public MySQLConnection(String databaseUrl, String user, String password) throws SQLException {
		super(databaseUrl, user, password);
	}

	public void initConnection() throws SQLException {
		Properties properties = new Properties();
		properties.put("user", user);
		properties.put("password", password);
		this.connection = new Driver().connect(databaseUrl, properties);
	}
}