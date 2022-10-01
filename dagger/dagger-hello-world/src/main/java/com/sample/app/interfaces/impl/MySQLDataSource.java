package com.sample.app.interfaces.impl;

import com.sample.app.interfaces.DataSource;

public class MySQLDataSource implements DataSource{

	@Override
	public String read() {
		return "hello world!!!!!";
	}

}
