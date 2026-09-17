package com.sample.app.agents;

import com.sample.app.model.DeploymentInfo;
import com.sample.app.model.IncidentTicket;
import com.sample.app.model.ServiceHealth;
import com.sample.app.service.TicketService;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public class TicketCreationAgent {

  private final TicketService ticketService;

  public TicketCreationAgent(TicketService ticketService) {

    this.ticketService = ticketService;
  }

  @Agent(
      value =
          """
                    Creates an incident ticket in the
                    incident-management system
                    """,
      outputKey = "ticket")
  public IncidentTicket createTicket(
      @V("incident") String incident,
      @V("serviceName") String serviceName,
      @V("priority") String priority,
      @V("rootCause") String rootCause,
      @V("serviceHealth") ServiceHealth serviceHealth,
      @V("deploymentInfo") DeploymentInfo deploymentInfo) {

    System.out.println();
    System.out.println(">>> NON-AI AGENT: TicketCreationAgent");

    return ticketService.createTicket(
        incident, serviceName, priority, rootCause, serviceHealth, deploymentInfo);
  }
}
