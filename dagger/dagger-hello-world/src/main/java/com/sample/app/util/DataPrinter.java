package com.sample.app.util;

import javax.inject.Inject;
import javax.inject.Named;

import com.sample.app.interfaces.DataSource;

public class DataPrinter {

	private DataSource sqlSource;

	private DataSource fileSource;

	@Inject
	public DataPrinter(@Named("sql") DataSource sqlSource, @Named("file") DataSource fileSource) {
		this.sqlSource = sqlSource;
		this.fileSource = fileSource;
	}

	public void print() {
		String dataFromSQL = sqlSource.read();
		String dataFromFile = fileSource.read();

		System.out.println(dataFromSQL);
		System.out.println(dataFromFile);
	}

}
