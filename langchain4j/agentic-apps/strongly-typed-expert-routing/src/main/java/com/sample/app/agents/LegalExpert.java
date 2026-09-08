package com.sample.app.agents;

import com.sample.app.keys.UserRequest;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.K;
import dev.langchain4j.service.UserMessage;

public interface LegalExpert {

  @UserMessage(
      """
			You are the Legal Expert Agent.

			Analyze the following request from a legal perspective.

			Explain the relevant legal concepts clearly.

			Do not pretend to know jurisdiction-specific facts that
			have not been provided by the user.

			When appropriate, explain that laws vary by jurisdiction
			and professional legal advice may be required.

			User request:

			{{UserRequest}}
			""")
  @Agent(
      name = "LegalExpert",
      description =
          "Handles law, legal rights, contracts, regulation, compliance, and legal questions")
  String answer(@K(UserRequest.class) String request);
}
