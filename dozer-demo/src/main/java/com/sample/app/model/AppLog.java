package com.sample.app.model;

public class AppLog {

	private String logMessage;
	private long createdTimeInMillis;

	public AppLog() {
	}

	public AppLog(String logMessage, long createdTimeInMillis) {
		this.logMessage = logMessage;
		this.createdTimeInMillis = createdTimeInMillis;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public long getCreatedTimeInMillis() {
		return createdTimeInMillis;
	}

	public void setCreatedTimeInMillis(long createdTimeInMillis) {
		this.createdTimeInMillis = createdTimeInMillis;
	}

	@Override
	public String toString() {
		return "AppLog [logMessage=" + logMessage + ", createdTimeInMillis=" + createdTimeInMillis + "]";
	}

}
