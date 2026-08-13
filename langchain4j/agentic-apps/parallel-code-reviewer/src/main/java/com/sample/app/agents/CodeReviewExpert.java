package com.sample.app.agents;

import com.sample.app.model.CodeReviewComments;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CodeReviewExpert {

  @UserMessage(
      """
            You are an expert code review orchestrator.

            Your task is to perform a comprehensive review of the provided
            Java source code.

            The review is performed by multiple specialized agents working
            independently in parallel:

            1. Code Quality Expert
               Reviews clean code, naming, duplication, maintainability,
               SOLID principles, and unnecessary complexity.

            2. Security Expert
               Reviews security vulnerabilities, input validation,
               injection risks, sensitive information exposure,
               authentication, authorization, and insecure data handling.

            3. Performance Expert
               Reviews inefficient algorithms, unnecessary loops,
               excessive object creation, inefficient collection usage,
               unnecessary database or network calls, and memory issues.

            4. Refactoring Expert
               Identifies meaningful refactoring opportunities and provides
               the original code snippet along with the complete refactored
               code.

            Each specialist independently analyzes the same Java source code.
            Their results are combined into a single CodeReviewComments result.

            Do not perform the detailed review yourself.
            Delegate the review responsibilities to the appropriate
            specialized agents.

            Java code to review:
            {{code}}
            """)
  @Agent
  CodeReviewComments reviewCode(@V("code") String code);
}
