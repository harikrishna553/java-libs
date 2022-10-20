package com.sample.app.event;

public class ConnectionCloseEvent {
	private String message;

	public ConnectionCloseEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
