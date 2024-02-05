package com.sample.app;

import java.util.UUID;

import com.sample.app.events.EventType;
import com.sample.app.events.TransactionEvent;
import com.sample.app.model.BankAccount;

public class App {

	public static void main(String[] args) {
		UUID accountId = UUID.randomUUID();
		BankAccount bankAccount = new BankAccount(accountId);

		// Simulate transactions
		TransactionEvent depositEvent = new TransactionEvent(UUID.randomUUID(), EventType.DEPOSIT, 100.0);
		bankAccount.applyEvent(depositEvent);

		TransactionEvent withdrawalEvent = new TransactionEvent(UUID.randomUUID(), EventType.WITHDRAWL, 50.0);
		bankAccount.applyEvent(withdrawalEvent);

		TransactionEvent anotherDepositEvent = new TransactionEvent(UUID.randomUUID(), EventType.DEPOSIT, 30.0);
		bankAccount.applyEvent(anotherDepositEvent);

		// Print final state and transaction history
		System.out.println("Final Balance: " + bankAccount.getBalance());
		System.out.println("Transaction History: ");
		bankAccount.getTransactionHistory().stream().forEach(System.out::println);

	}
}
