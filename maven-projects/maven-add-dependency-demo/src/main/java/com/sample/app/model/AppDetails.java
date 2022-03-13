package com.sample.app.model;

public class AppDetails {
	private String appName;
	private String version;

	public AppDetails() {
	}

	public AppDetails(String appName, String version) {
		this.appName = appName;
		this.version = version;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
