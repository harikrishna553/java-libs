package com.sample.app.events;

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
