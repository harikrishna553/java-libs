package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CreditAgent {

  @Agent(
      description = "Credits money into the destination bank account",
      outputKey = "creditResult")
  @UserMessage(
      """
        Credit {{amount}} dollars to the account {{destinationAccount}}.
        Use the available banking tool to perform the credit operation.
        """)
  String credit(@V("destinationAccount") String destinationAccount, @V("amount") Double amount);
}
