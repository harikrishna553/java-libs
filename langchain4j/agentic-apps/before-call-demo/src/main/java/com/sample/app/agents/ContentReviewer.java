package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ContentReviewer {

  @UserMessage(
      """
			Review and improve the following content.

			Content Type:
			{{contentType}}

			Target Audience:
			{{audience}}

			Expected Tone:
			{{tone}}

			Draft:
			{{draft}}

			Review the content for:
			- clarity
			- grammar
			- structure
			- relevance
			- consistency with the expected tone
			- suitability for the target audience

			Return only the final improved content.
			""")
  @Agent(
      name = "contentReviewer",
      description = "Reviews and improves the generated content.",
      outputKey = "finalContent")
  String reviewContent(
      @V("draft") String draft,
      @V("contentType") String contentType,
      @V("audience") String audience,
      @V("tone") String tone);
}
