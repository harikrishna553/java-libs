package com.sample.app.modules;

import javax.inject.Named;

import com.sample.app.interfaces.DataSource;
import com.sample.app.interfaces.impl.FileDataSource;
import com.sample.app.interfaces.impl.MySQLDataSource;

import dagger.Module;
import dagger.Provides;

@Module
public class DependenciesProviderModule {
	
	@Provides
	@Named("sql")
	public static DataSource provideMySqlDataSource() {
		return new MySQLDataSource();
	}
	
	@Provides
	@Named("file")
	public static DataSource provideFileDataSource() {
		return new FileDataSource();
	}
}
