package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface SecurityReviewAgent {

  @UserMessage(
      """
            You are a senior application security architect.

            Review the following software architecture from a security perspective.

            Identify:
            - Authentication and authorization risks
            - Data protection concerns
            - Network and API security risks
            - Secrets management issues
            - Potential attack surfaces
            - Recommended security improvements

            Software architecture:
            {{design}}

            Provide a concise but actionable security review.
            """)
  @Agent(
      description = "Reviews a software architecture for security risks and recommendations",
      outputKey = "securityReview")
  String review(@V("design") String design);
}
