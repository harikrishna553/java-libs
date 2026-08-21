package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ArchitectureAssessmentAgent {

  @UserMessage(
      """
			You are a senior software architect.

			Perform an overall architecture assessment using the
			following software design and specialist reviews.

			Software architecture:
			{{design}}

			Security review:
			{{securityReview}}

			Scalability review:
			{{scalabilityReview}}

			Based on these inputs, evaluate:

			- Overall architecture quality
			- Major architectural risks
			- Design inconsistencies
			- Reliability concerns
			- Maintainability
			- Recommended architectural improvements

			Provide a consolidated architecture assessment.
			""")
  @Agent(
      description =
          "Performs an overall architecture assessment using security and scalability reviews",
      outputKey = "architectureAssessment")
  String assess(
      @V("design") String design,
      @V("securityReview") String securityReview,
      @V("scalabilityReview") String scalabilityReview);
}
