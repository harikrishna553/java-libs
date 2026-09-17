package com.sample.app.agents;

import com.sample.app.model.DeploymentInfo;
import com.sample.app.model.ServiceHealth;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface RootCauseAnalyzerAgent {

  @SystemMessage(
      """
			You are a senior site reliability engineer.

			Analyze production incidents using the evidence
			provided to you.

			Requirements:

			- Do not invent facts.
			- Base the analysis only on the supplied evidence.
			- Clearly distinguish evidence from hypothesis.
			- Keep the answer concise.
			- Mention whether the latest deployment could
			  reasonably be related to the incident.
			- Suggest the next investigation step.
			""")
  @UserMessage(
      """
			Analyze this production incident.

			INCIDENT
			--------
			{{incident}}

			SERVICE
			-------
			{{serviceName}}

			CURRENT SERVICE HEALTH
			----------------------
			{{serviceHealth}}

			LATEST DEPLOYMENT
			-----------------
			{{deploymentInfo}}

			Produce a concise root-cause analysis.
			""")
  @Agent(
      value =
          """
			Investigates an incident using service
			health and deployment information
			""",
      outputKey = "rootCause")
  String analyze(
      @V("incident") String incident,
      @V("serviceName") String serviceName,
      @V("serviceHealth") ServiceHealth serviceHealth,
      @V("deploymentInfo") DeploymentInfo deploymentInfo);
}
