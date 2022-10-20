package com.sample.app.event;

public class ConnectionCreateEvent {
	private String message;

	public ConnectionCreateEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
