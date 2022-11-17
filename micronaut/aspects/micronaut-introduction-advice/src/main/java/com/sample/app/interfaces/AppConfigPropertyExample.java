package com.sample.app.interfaces;

import com.sample.app.advices.AppConfigProperty;

@AppConfigProperty
public interface AppConfigPropertyExample {

	@AppConfigProperty("runningEnvironment")
	String runningEnvironment();
	
	@AppConfigProperty("appName")
	String applicationName();

}