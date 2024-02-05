package com.sample.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sample.app.events.EventType;
import com.sample.app.events.TransactionEvent;

public class BankAccount {
	private UUID accountId;
	private List<TransactionEvent> transactionHistory;

	public BankAccount(UUID accountId) {
		this.accountId = accountId;
		this.transactionHistory = new ArrayList<>();
	}

	// Apply a transaction event to update the state
	public synchronized void applyEvent(TransactionEvent event) {
		double balance = getBalance();

		if (event.getType() == EventType.DEPOSIT) {
			if (event.getAmount() <= 0) {
				throw new IllegalArgumentException("Deposit amount must be positive");
			}
			balance += event.getAmount();
		} else if (event.getType() == EventType.WITHDRAWL) {
			if (balance < event.getAmount()) {
				throw new IllegalArgumentException("Balance is less than withdrawl amount");
			}
			balance -= event.getAmount();
		}

		transactionHistory.add(event);
	}

	public UUID getAccountId() {
		return accountId;
	}

	public synchronized double getBalance() {
		double balance = 0.0;
		for (TransactionEvent event : transactionHistory) {
			if (event.getType() == EventType.DEPOSIT) {
				balance += event.getAmount();
			} else if (event.getType() == EventType.WITHDRAWL) {
				balance -= event.getAmount();
			}
		}
		return balance;
	}

	public List<TransactionEvent> getTransactionHistory() {
		return new ArrayList<>(transactionHistory);
	}
}

