package com.sample.app.events.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sample.app.events.AccountCreatedEvent;
import com.sample.app.events.Event;
import com.sample.app.events.MoneyDepositedEvent;
import com.sample.app.events.MoneyWithdrawnEvent;
import com.sample.app.model.Snapshot;

public class EventStoreWithSnapshots extends EventStore {
	private final List<Snapshot> snapshots = new ArrayList<>();

	public List<Event> getEvents(String accountId, LocalDateTime after) {
		List<Event> accountEvents = new ArrayList<>();
		for (Event event : this.getEvents()) {
			if (event instanceof AccountCreatedEvent && ((AccountCreatedEvent) event).getAccountId().equals(accountId)
					&& event.getTimestamp().isAfter(after)) {
				accountEvents.add(event);
			} else if (event instanceof MoneyDepositedEvent
					&& ((MoneyDepositedEvent) event).getAccountId().equals(accountId)
					&& event.getTimestamp().isAfter(after)) {
				accountEvents.add(event);
			} else if (event instanceof MoneyWithdrawnEvent
					&& ((MoneyWithdrawnEvent) event).getAccountId().equals(accountId)
					&& event.getTimestamp().isAfter(after)) {
				accountEvents.add(event);
			}
		}
		return accountEvents;
	}

	public void saveSnapshot(Snapshot snapshot) {
		snapshots.add(snapshot);
	}

	public Optional<Snapshot> getLatestSnapshot(String accountId) {
		return snapshots.stream().filter(snapshot -> snapshot.getAccountId().equals(accountId))
				.max((s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp()));
	}
}
