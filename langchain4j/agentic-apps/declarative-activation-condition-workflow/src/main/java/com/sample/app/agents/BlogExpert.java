package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface BlogExpert {

  @UserMessage(
      """
			You are a professional blog writer.

			Create a well-structured blog post for the following request:

			{{request}}

			Requirements:

			- Provide a suitable title.
			- Include a clear introduction.
			- Organize the content into meaningful sections.
			- Keep the language professional and easy to understand.
			- Provide a concise conclusion.

			Return only the final blog post.
			""")
  @Agent(
      name = "blogExpert",
      description = "Creates professional blog content",
      outputKey = "content")
  String createBlog(@V("request") String request);
}
