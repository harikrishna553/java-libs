package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import java.util.List;

public interface CodeQualityExpert {

  @UserMessage(
      """
            You are an expert Java code reviewer.

            Review the following Java code specifically for code quality.

            Look for:
            - Clean code violations
            - Poor naming
            - Code duplication
            - Maintainability issues
            - SOLID principle violations
            - Unnecessary complexity

            Provide a concise list of findings.
            Do not review security or performance.

            Java code:
            {{code}}
            """)
  @Agent
  List<String> reviewCodeQuality(@V("code") String code);
}
