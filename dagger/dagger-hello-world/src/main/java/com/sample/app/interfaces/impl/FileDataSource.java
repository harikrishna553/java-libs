package com.sample.app.interfaces.impl;

import com.sample.app.interfaces.DataSource;

public class FileDataSource implements DataSource{

	@Override
	public String read() {
		return "Hi there!!!!!";
	}

}
