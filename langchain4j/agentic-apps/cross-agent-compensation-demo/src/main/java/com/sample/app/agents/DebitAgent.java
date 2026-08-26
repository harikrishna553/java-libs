package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface DebitAgent {

  @Agent(description = "Withdraws money from the source bank account", outputKey = "debitResult")
  @UserMessage(
      """
        Withdraw {{amount}} dollars from the account {{sourceAccount}}.

        You MUST use the banking tool to perform the withdrawal.
        Do not just describe the operation.

        Return the result of the withdrawal.
        """)
  String debit(@V("sourceAccount") String sourceAccount, @V("amount") Double amount);
}
