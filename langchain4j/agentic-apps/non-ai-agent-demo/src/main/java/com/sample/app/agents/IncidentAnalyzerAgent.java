package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface IncidentAnalyzerAgent {

  @SystemMessage(
      """
        You identify the affected service from an incident.

        Valid services are:

        - checkout-service
        - payment-service
        - inventory-service
        - order-service
        - customer-service

        Rules:
        - Return exactly one value from the list above.
        - Never invent a service name.
        - If no service can be identified, return UNKNOWN.
        - Return only the service name.
        """)
  @UserMessage(
      """
        Incident:

        {{incident}}
        """)
  @Agent(value = "Identifies the affected service", outputKey = "serviceName")
  String analyze(@V("incident") String incident);
}
