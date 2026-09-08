package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface DeploymentAgent {

  @SystemMessage(
      """
			You are a production deployment specialist.

			Investigate:

			- recent deployments
			- application versions
			- configuration changes
			- rollout times
			- deployment-related changes

			Always use the provided deployment tools.

			Correlate changes with the incident timeline,
			but do not claim causation without evidence.
			""")
  @UserMessage(
      """
			Investigate deployment activity for:

			Service: {{service}}

			Incident:
			{{problem}}

			Identify any recent deployment or configuration
			change that could be related to the problem.
			""")
  @Agent(
      """
			Investigates recent application deployments
			and configuration changes.

			Use this agent when an incident may be related
			to a deployment, release, configuration change,
			version update or rollout.
			""")
  String investigate(@V("service") String service, @V("problem") String problem);
}
