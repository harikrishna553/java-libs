package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface NotificationAgent {

  @Agent(
      description = "Sends a notification after the bank transfer is completed",
      outputKey = "notificationResult")
  @UserMessage(
      """
        Send a notification for the transfer to {{destinationAccount}}
        for an amount of {{amount}} dollars.

        You MUST use the notification tool to send the notification.
        Do not just describe the notification.

        Return the result of the notification operation.
        """)
  String notify(@V("destinationAccount") String destinationAccount, @V("amount") Double amount);
}
