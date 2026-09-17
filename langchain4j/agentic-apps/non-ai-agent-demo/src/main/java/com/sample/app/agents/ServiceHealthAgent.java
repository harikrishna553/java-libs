package com.sample.app.agents;

import com.sample.app.model.ServiceHealth;
import com.sample.app.service.MonitoringService;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public class ServiceHealthAgent {

  private final MonitoringService monitoringService;

  public ServiceHealthAgent(MonitoringService monitoringService) {

    this.monitoringService = monitoringService;
  }

  @Agent(
      value =
          """
			Retrieves the current health metrics
			for the specified service
			""",
      outputKey = "serviceHealth")
  public ServiceHealth checkHealth(@V("serviceName") String serviceName) {

    System.out.println();
    System.out.println(">>> NON-AI AGENT: ServiceHealthAgent");

    return monitoringService.getHealth(serviceName);
  }
}
