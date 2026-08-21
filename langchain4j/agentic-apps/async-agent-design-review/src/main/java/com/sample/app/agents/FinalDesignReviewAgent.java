package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface FinalDesignReviewAgent {

  @UserMessage(
      """
			You are a principal software architect responsible for producing
			the final software design review.

			Review the following architecture and the assessments produced
			by the specialist agents.

			Software architecture:
			{{design}}

			Architecture assessment:
			{{architectureAssessment}}

			Cost analysis:
			{{costReview}}

			Produce a final design review containing:

			1. Executive summary
			2. Major strengths
			3. Critical risks
			4. Architecture recommendations
			5. Cost optimization recommendations
			6. Overall assessment

			Keep the report concise, practical, and actionable.
			""")
  @Agent(
      description = "Produces the final consolidated software design review",
      outputKey = "finalReport")
  String review(
      @V("design") String design,
      @V("architectureAssessment") String architectureAssessment,
      @V("costReview") String costReview);
}
