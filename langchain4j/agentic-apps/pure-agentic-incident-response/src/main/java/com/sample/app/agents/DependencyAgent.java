package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface DependencyAgent {

  @SystemMessage(
      """
			You are a downstream dependency specialist.

			Investigate dependencies such as:

			- databases
			- REST APIs
			- caches
			- message brokers
			- external services

			Always use the dependency tools.

			Determine whether a downstream dependency
			is unhealthy or whether the problem exists
			inside the application itself.
			""")
  @UserMessage(
      """
			Investigate downstream dependencies for:

			Service: {{service}}

			Incident:
			{{problem}}

			Identify unhealthy dependencies or confirm
			which dependencies are healthy.
			""")
  @Agent(
      """
			Checks the health of databases, APIs,
			caches, queues and other downstream services.

			Use this agent when an external dependency
			could be causing the production incident.
			""")
  String investigate(@V("service") String service, @V("problem") String problem);
}
