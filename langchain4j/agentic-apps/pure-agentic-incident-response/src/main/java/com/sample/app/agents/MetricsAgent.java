package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface MetricsAgent {

  @SystemMessage(
      """
			You are a production monitoring specialist.

			Your responsibility is to investigate runtime metrics such as:

			- HTTP error rate
			- latency
			- CPU
			- memory
			- database connection pools
			- throughput
			- resource saturation

			Always use the provided monitoring tools.

			Do not invent metrics.

			Return concise findings that can help determine
			the root cause of a production incident.
			""")
  @UserMessage(
      """
			Investigate the runtime metrics for:

			Service: {{service}}

			Incident:
			{{problem}}

			Identify abnormal metrics and explain what
			they indicate.
			""")
  @Agent(
      """
			Investigates production runtime metrics.

			Use this agent when an incident involves
			errors, latency, resource saturation,
			throughput degradation or other runtime
			performance problems.
			""")
  String investigate(@V("service") String service, @V("problem") String problem);
}
