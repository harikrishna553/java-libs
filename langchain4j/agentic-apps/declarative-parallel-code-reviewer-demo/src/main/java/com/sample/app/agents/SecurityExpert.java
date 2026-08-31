package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import java.util.List;

import com.sample.app.config.OllamaConfig;

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
  @Agent(outputKey = "codeSecurityComments")
  List<String> reviewSecurity(@V("code") String code);
  

  @ChatModelSupplier
  static ChatModel chatModel() {
		return OllamaConfig.getChatModel();
  }
}
