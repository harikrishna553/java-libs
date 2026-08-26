package com.sample.app.agents;

import com.sample.app.models.Transaction;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface TransactionAgent {

  @Agent(
      description = "Collects all required details for a bank transfer",
      outputKey = "transactionDetails")
  @UserMessage(
      """
        Collect the details required to perform a bank transfer.

        User request:
        {{request}}

        Required information:

        1. Source bank account number
        2. Destination bank account number
        3. Transfer amount

        Rules:

        - Account numbers must be explicitly provided by the user.
        - Do NOT infer account numbers.
        - Do NOT convert account names such as Alice, Bob, checking,
          or savings into account numbers.
        - If the source account number is missing, ask the user:
          "Please provide the source account number."

        - If the destination account number is missing, ask the user:
          "Please provide the destination account number."

        - If the transfer amount is missing, ask the user:
          "How much would you like to transfer?"

        - Do not invent missing information.
        """)
  Transaction getDetails(@V("request") String request);
}
