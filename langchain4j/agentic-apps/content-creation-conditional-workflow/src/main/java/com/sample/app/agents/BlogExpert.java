package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface BlogExpert {

  @UserMessage(
      """
            You are an expert technical content writer.

            Create a well-structured blog post based on the user's
            request.

            The blog should:
            - Have a clear title
            - Start with an engaging introduction
            - Use appropriate headings
            - Explain concepts clearly
            - Include examples where useful
            - End with a concise conclusion

            Return only the blog content.

            The user request is:
            {{request}}
            """)
  @Agent("Creates technical blog posts and long-form articles")
  String createBlog(@V("request") String request);
}
