package com.sample.app.events;

import java.time.LocalDateTime;

public class MoneyDepositedEvent extends Event {
	private final String accountId;
	private final double amount;

	public MoneyDepositedEvent(LocalDateTime timestamp, String accountId, double amount) {
		super(timestamp);
		this.accountId = accountId;
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "MoneyDeposited [accountId=" + accountId + ", amount=" + amount + " " + super.toString() +"]";
	}

}