package com.sample.app.events;

import java.util.Date;
import java.util.UUID;

public class TransactionEvent {
	private UUID transactionId;
	private EventType type;
	private double amount;
	private Date date;

	public TransactionEvent(UUID transactionId, EventType type, double amount) {
		this.transactionId = transactionId;
		this.type = type;
		this.amount = amount;
		this.date = new Date();
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public EventType getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("|");
		builder.append(transactionId).append("|");
		builder.append(type).append("|");
		builder.append(amount).append("|");
		builder.append(date).append("|");

		return builder.toString();
	}

}
