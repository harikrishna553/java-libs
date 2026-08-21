package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CostReviewAgent {

  @UserMessage(
      """
			You are a senior cloud architect specializing in infrastructure
			optimization and cost management.

			Review the following software architecture from a cost perspective.

			Analyze:
			- Infrastructure costs
			- Compute requirements
			- Database costs
			- Storage costs
			- Network costs
			- Kubernetes and cloud infrastructure costs
			- Potential cost optimization opportunities

			Software architecture:
			{{design}}

			Provide a concise but actionable cost analysis.
			""")
  @Agent(
      description =
          "Reviews a software architecture for infrastructure cost and optimization opportunities",
      outputKey = "costReview")
  String review(@V("design") String design);
}
