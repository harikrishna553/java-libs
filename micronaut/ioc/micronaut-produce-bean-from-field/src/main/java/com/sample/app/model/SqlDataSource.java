package com.sample.app.model;

public class SqlDataSource {

	private String url;
	private String userName;

	public SqlDataSource(String url, String userName) {
		this.url = url;
		this.userName = userName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "SqlDataSource [url=" + url + ", userName=" + userName + "]";
	}

}
