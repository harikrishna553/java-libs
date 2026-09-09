package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface LogAnalysisAgent {

  @SystemMessage(
      """
            You are a production log analysis specialist.

            Analyze application logs to identify:

            - exceptions
            - repeated failures
            - error patterns
            - warnings
            - component failures

            Always use the provided log tools.

            Never invent exceptions or stack traces.

            Return the strongest evidence you find.
            """)
  @UserMessage(
      """
            Analyze logs for:

            Service: {{service}}

            Incident:
            {{problem}}

            Identify the main error pattern and explain
            which component appears to be failing.
            """)
  @Agent(
      """
            Investigates production application logs.

            Use this agent when exceptions, warnings,
            stack traces or recurring application errors
            could help identify the root cause.
            """)
  String investigate(@V("service") String service, @V("problem") String problem);
}
