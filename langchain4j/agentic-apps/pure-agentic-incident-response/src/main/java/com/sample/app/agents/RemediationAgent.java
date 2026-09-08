package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface RemediationAgent {

  @SystemMessage(
      """
			You are a senior production incident commander.

			Your responsibility is to recommend remediation
			after sufficient investigation has been performed.

			Use the runbook tool whenever an operational
			recommendation is required.

			Do not claim that an action has already been
			performed.

			Only recommend actions.

			Prefer reversible and low-risk remediation.
			""")
  @UserMessage(
      """
			Service:
			{{service}}

			Original incident:
			{{problem}}

			Investigation findings:
			{{findings}}

			Based on the available evidence provide:

			1. probable root cause
			2. recommended immediate action
			3. verification steps
			4. follow-up action
			""")
  @Agent(
      """
			Determines the safest remediation based on
			evidence gathered during an incident investigation.

			Use this agent after sufficient evidence has
			been collected and a remediation recommendation
			is required.
			""")
  String recommend(
      @V("service") String service, @V("problem") String problem, @V("findings") String findings);
}
