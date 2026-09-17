package com.sample.app.service;

import com.sample.app.model.DeploymentInfo;
import com.sample.app.model.IncidentTicket;
import com.sample.app.model.ServiceHealth;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketService {

  private final AtomicInteger sequence = new AtomicInteger(1000);

  public IncidentTicket createTicket(
      String incident,
      String serviceName,
      String priority,
      String rootCause,
      ServiceHealth serviceHealth,
      DeploymentInfo deploymentInfo) {

    String ticketId = "INC-" + sequence.incrementAndGet();

    System.out.println();
    System.out.println("[TicketService] Creating ticket " + ticketId);

    // In a real system:
    //
    // POST /servicenow/incidents
    // POST /jira/rest/api/...
    // repository.save(...)

    return new IncidentTicket(
        ticketId,
        serviceName,
        priority,
        incident,
        rootCause,
        serviceHealth,
        deploymentInfo,
        LocalDateTime.now());
  }
}
