package com.sample.app.events.util;

import java.util.ArrayList;
import java.util.List;

import com.sample.app.events.Event;

public class EventStore {
	private final List<Event> events = new ArrayList<>();

	public void saveEvent(Event event) {
		events.add(event);
	}

	public List<Event> getEvents() {
		return new ArrayList<>(events);
	}
}