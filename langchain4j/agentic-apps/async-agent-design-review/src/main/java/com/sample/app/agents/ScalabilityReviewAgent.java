package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ScalabilityReviewAgent {

  @UserMessage(
      """
            You are a senior distributed systems and scalability architect.

            Review the following software architecture from a scalability
            and performance perspective.

            Analyze:
            - Potential scalability bottlenecks
            - Horizontal and vertical scaling
            - Database scalability
            - Caching strategy
            - Message processing
            - Load balancing
            - Availability and resilience
            - Performance risks

            Software architecture:
            {{design}}

            Provide a concise but actionable scalability review.
            """)
  @Agent(
      description = "Reviews a software architecture for scalability and performance",
      outputKey = "scalabilityReview")
  String review(@V("design") String design);
}
