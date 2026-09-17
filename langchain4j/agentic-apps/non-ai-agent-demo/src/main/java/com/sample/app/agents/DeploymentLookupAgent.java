package com.sample.app.agents;

import com.sample.app.model.DeploymentInfo;
import com.sample.app.service.DeploymentService;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public class DeploymentLookupAgent {

  private final DeploymentService deploymentService;

  public DeploymentLookupAgent(DeploymentService deploymentService) {

    this.deploymentService = deploymentService;
  }

  @Agent(
      value =
          """
			Retrieves the most recent deployment
			for the specified service
			""",
      outputKey = "deploymentInfo")
  public DeploymentInfo findLatestDeployment(@V("serviceName") String serviceName) {

    System.out.println();
    System.out.println(">>> NON-AI AGENT: DeploymentLookupAgent");

    return deploymentService.getLatestDeployment(serviceName);
  }
}
