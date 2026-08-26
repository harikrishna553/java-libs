package com.sample.app.models;

public class Transaction {

  private String sourceAccount;
  private String destinationAccount;
  private Double amount;

  public String getSourceAccount() {
    return sourceAccount;
  }

  public void setSourceAccount(String sourceAccount) {
    this.sourceAccount = sourceAccount;
  }

  public String getDestinationAccount() {
    return destinationAccount;
  }

  public void setDestinationAccount(String destinationAccount) {
    this.destinationAccount = destinationAccount;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "Transaction [sourceAccount="
        + sourceAccount
        + ", destinationAccount="
        + destinationAccount
        + ", amount="
        + amount
        + "]";
  }
}
