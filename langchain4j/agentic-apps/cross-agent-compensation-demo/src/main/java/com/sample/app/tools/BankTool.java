package com.sample.app.tools;

import com.sample.app.models.AccountDetails;
import dev.langchain4j.agent.tool.CompensateFor;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import java.util.HashMap;
import java.util.Map;

public class BankTool {

  /*
   * Key = accountId Value = AccountDetails
   */
  private final Map<String, AccountDetails> accounts = new HashMap<>();

  /** Create 10 sample accounts when BankTool is created. */
  public BankTool() {

    createAccount("ACC-001", "Alice", 1_000.0);
    createAccount("ACC-002", "Bob", 500.0);
    createAccount("ACC-003", "Charlie", 2_500.0);
    createAccount("ACC-004", "David", 750.0);
    createAccount("ACC-005", "Emma", 1_500.0);
    createAccount("ACC-006", "Frank", 3_000.0);
    createAccount("ACC-007", "Grace", 900.0);
    createAccount("ACC-008", "Henry", 1_200.0);
    createAccount("ACC-009", "Ivy", 650.0);
    createAccount("ACC-010", "Jack", 4_000.0);
  }

  /** Remove all accounts. */
  public void clearAccounts() {
    accounts.clear();
  }

  /** Create a new bank account. */
  public void createAccount(String accountId, String accountHolderName, Double initialBalance) {

    if (accounts.containsKey(accountId)) {
      throw new RuntimeException("Account with ID " + accountId + " already exists");
    }

    AccountDetails account = new AccountDetails(accountId, accountHolderName, initialBalance);

    accounts.put(accountId, account);

    System.out.println(
        "[BANK] Created account: "
            + accountId
            + " | Holder: "
            + accountHolderName
            + " | Balance: $"
            + initialBalance);
  }

  /** Get account details. */
  public AccountDetails getAccount(String accountId) {

    AccountDetails account = accounts.get(accountId);

    if (account == null) {
      throw new RuntimeException("No account found with ID " + accountId);
    }

    return account;
  }

  /** Get the current account balance. */
  public double getBalance(String accountId) {

    return getAccount(accountId).getBalance();
  }

  /**
   * Credit money into an account.
   *
   * <p>This operation has a compensation method: reverseCredit()
   */
  @Tool(
      "Credit the given amount to the bank account identified by accountId "
          + "and return the new balance")
  public Double credit(@P("account ID") String accountId, @P("amount") Double amount) {

    AccountDetails account = getAccount(accountId);

    double currentBalance = account.getBalance();
    double newBalance = currentBalance + amount;

    account.setBalance(newBalance);

    System.out.println(
        "[BANK] CREDIT $"
            + amount
            + " -> Account: "
            + accountId
            + " | Current Balance = $"
            + currentBalance
            + " | New Balance = $"
            + newBalance);

    return newBalance;
  }

  /**
   * Compensation for credit().
   *
   * <p>If credit() succeeded but a later agent fails, LangChain4j can call this method
   * automatically.
   */
  @CompensateFor("credit")
  public void reverseCredit(String accountId, Double amount) {

    AccountDetails account = accounts.get(accountId);

    if (account != null) {

      double currentBalance = account.getBalance();
      double newBalance = currentBalance - amount;

      account.setBalance(newBalance);

      System.out.println(
          "[COMPENSATION] REVERSE CREDIT $"
              + amount
              + " <- Account: "
              + accountId
              + " | Current Balance = $"
              + currentBalance
              + " | New Balance = $"
              + newBalance);
    }
  }

  /**
   * Withdraw money from an account.
   *
   * <p>This operation has a compensation method: reverseWithdraw()
   */
  @Tool(
      "Withdraw the given amount from the bank account identified by accountId "
          + "and return the new balance")
  public Double withdraw(@P("account ID") String accountId, @P("amount") Double amount) {

    AccountDetails account = getAccount(accountId);

    double currentBalance = account.getBalance();

    if (currentBalance < amount) {
      throw new RuntimeException("Insufficient balance for account " + accountId);
    }

    double newBalance = currentBalance - amount;

    account.setBalance(newBalance);

    System.out.println(
        "[BANK] WITHDRAW $"
            + amount
            + " <- Account: "
            + accountId
            + " | Current Balance = $"
            + currentBalance
            + " | New Balance = $"
            + newBalance);

    return newBalance;
  }

  /**
   * Compensation for withdraw().
   *
   * <p>This puts the withdrawn money back.
   */
  @CompensateFor("withdraw")
  public void reverseWithdraw(String accountId, Double amount) {

    AccountDetails account = accounts.get(accountId);

    if (account != null) {

      double currentBalance = account.getBalance();
      double newBalance = currentBalance + amount;

      account.setBalance(newBalance);

      System.out.println(
          "[COMPENSATION] REVERSE WITHDRAW $"
              + amount
              + " -> Account: "
              + accountId
              + " | Current Balance = $"
              + currentBalance
              + " | New Balance = $"
              + newBalance);
    }
  }

  /** Tool that displays all registered bank accounts. */
  @Tool("Display all registered bank accounts and their current balances")
  public String displayAllAccounts() {

    StringBuilder result = new StringBuilder();

    result.append("\n========== REGISTERED ACCOUNTS ==========\n");

    for (AccountDetails account : accounts.values()) {

      result.append("Account ID : ").append(account.getAccountId()).append("\n");

      result.append("Holder     : ").append(account.getAccountHolderName()).append("\n");

      result.append("Balance    : $").append(account.getBalance()).append("\n");

      result.append("-----------------------------------------\n");
    }

    result.append("Total Accounts: ").append(accounts.size()).append("\n");

    return result.toString();
  }
}
