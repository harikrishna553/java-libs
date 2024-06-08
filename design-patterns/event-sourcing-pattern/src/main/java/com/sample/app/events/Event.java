package com.sample.app.events;

import java.time.LocalDateTime;

public abstract class Event {
	private final LocalDateTime timestamp;

	protected Event(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "timestamp=" + timestamp ;
	}

}