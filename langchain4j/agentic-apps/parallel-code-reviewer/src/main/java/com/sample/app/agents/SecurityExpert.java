package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import java.util.List;

public interface SecurityExpert {

  @UserMessage(
      """
            You are an expert application security reviewer.

            Review the following Java code specifically for security vulnerabilities.

            Look for:
            - Injection vulnerabilities
            - Improper input validation
            - Sensitive information exposure
            - Authentication or authorization issues
            - Insecure handling of data
            - Other common security problems

            Provide a concise list of findings.
            Do not review general code quality or performance.

            Java code:
            {{code}}
            """)
  @Agent
  List<String> reviewSecurity(@V("code") String code);
}
