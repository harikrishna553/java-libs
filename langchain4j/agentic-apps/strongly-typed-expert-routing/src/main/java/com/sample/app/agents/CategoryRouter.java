package com.sample.app.agents;

import com.sample.app.enums.RequestCategory;
import com.sample.app.keys.Category;
import com.sample.app.keys.UserRequest;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.K;
import dev.langchain4j.service.UserMessage;

public interface CategoryRouter {

  @UserMessage(
      """
			Analyze the following user request.

			Classify the request into exactly one of these categories:

			MEDICAL
			- Health
			- Medicine
			- Symptoms
			- Injury
			- Treatment
			- General medical questions

			TECHNICAL
			- Software
			- Programming
			- Computers
			- Cloud
			- Networking
			- Databases
			- Technology

			LEGAL
			- Law
			- Legal rights
			- Contracts
			- Regulations
			- Compliance
			- Legal disputes

			Return ONLY one of these values:

			MEDICAL
			TECHNICAL
			LEGAL

			Do not provide any explanation.

			User request:

			{{UserRequest}}
			""")
  @Agent(
      name = "CategoryRouter",
      description = "Analyzes a user request and identifies the appropriate expert category",
      typedOutputKey = Category.class)
  RequestCategory classify(@K(UserRequest.class) String request);
}
