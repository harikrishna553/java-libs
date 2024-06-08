package com.sample.app.events;

import java.time.LocalDateTime;

public class AccountCreatedEvent extends Event {
	private final String accountId;
	private final String owner;

	public AccountCreatedEvent(LocalDateTime timestamp, String accountId, String owner) {
		super(timestamp);
		this.accountId = accountId;
		this.owner = owner;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getOwner() {
		return owner;
	}

	@Override
	public String toString() {
		return "AccountCreated [accountId=" + accountId + ", owner=" + owner + " " + super.toString() + "]";
	}

}
