package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import java.util.List;

import com.sample.app.config.OllamaConfig;

public interface PerformanceExpert {

	@UserMessage("""
			You are an expert Java performance reviewer.

			Review the following Java code specifically for performance issues.

			Look for:
			- Inefficient algorithms
			- Unnecessary loops
			- Excessive object creation
			- Inefficient collection usage
			- Unnecessary database or network calls
			- Potential memory issues
			- Other performance bottlenecks

			Provide a concise list of findings.
			Do not review security or general code quality.

			Java code:
			{{code}}
			""")
	@Agent(outputKey = "codePerformanceComments")
	List<String> reviewPerformance(@V("code") String code);

	@ChatModelSupplier
	static ChatModel chatModel() {
		return OllamaConfig.getChatModel();
	}
}
