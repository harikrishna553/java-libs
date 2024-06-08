package com.sample.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sample.app.events.AccountCreatedEvent;
import com.sample.app.events.Event;
import com.sample.app.events.MoneyDepositedEvent;
import com.sample.app.events.MoneyWithdrawnEvent;
import com.sample.app.events.util.EventStore;

public class BankAccount {
	private final String accountId;
	private final String owner;
	private double balance;
	private final EventStore eventStore;

	public BankAccount(String accountId, String owner) {
		this.accountId = accountId;
		this.owner = owner;
		this.balance = 0.0;
		this.eventStore = new EventStore();
	}

	public Event createAccount() {
		Event event = new AccountCreatedEvent(LocalDateTime.now(), accountId, owner);
		apply(event);
		return event;
	}

	public Event depositMoney(double amount) {
		Event event = new MoneyDepositedEvent(LocalDateTime.now(), accountId, amount);
		apply(event);
		return event;
	}

	public Event withdrawMoney(double amount) {
		if (balance >= amount) {
			Event event = new MoneyWithdrawnEvent(LocalDateTime.now(), accountId, amount);
			apply(event);
			return event;
		} else {
			throw new IllegalArgumentException("Insufficient funds");
		}
	}

	private void apply(Event event) {
		if (event instanceof AccountCreatedEvent) {
			this.balance = 0.0;
		} else if (event instanceof MoneyDepositedEvent) {
			this.balance += ((MoneyDepositedEvent) event).getAmount();
		} else if (event instanceof MoneyWithdrawnEvent) {
			this.balance -= ((MoneyWithdrawnEvent) event).getAmount();
		}
		this.eventStore.saveEvent(event);
	}

	public double getBalance() {
		return balance;
	}

	public List<Event> getEvents() {
		return new ArrayList<>(eventStore.getEvents());
	}

	public void printDetailedSummary() {
		for (Event event : eventStore.getEvents()) {
			System.out.println(event);
		}

		System.out.println("Total Balance : " + balance);
	}
}