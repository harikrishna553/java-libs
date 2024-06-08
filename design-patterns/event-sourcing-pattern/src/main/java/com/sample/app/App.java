package com.sample.app;

import com.sample.app.model.BankAccount;

public class App {

	public static void main(String args[]) {
		BankAccount bankAccount = new BankAccount("HARI123", "Harikrishna");
		bankAccount.createAccount();

		bankAccount.depositMoney(1000);
		bankAccount.depositMoney(2000);
		bankAccount.withdrawMoney(500);

		bankAccount.printDetailedSummary();
	}

}
