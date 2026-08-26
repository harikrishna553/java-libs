package com.sample.app.models;

public class AccountDetails {

  private final String accountId;
  private final String accountHolderName;
  private double balance;

  public AccountDetails(String accountId, String accountHolderName, double balance) {

    this.accountId = accountId;
    this.accountHolderName = accountHolderName;
    this.balance = balance;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getAccountHolderName() {
    return accountHolderName;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "AccountDetails{"
        + "accountId='"
        + accountId
        + '\''
        + ", accountHolderName='"
        + accountHolderName
        + '\''
        + ", balance="
        + balance
        + '}';
  }
}
