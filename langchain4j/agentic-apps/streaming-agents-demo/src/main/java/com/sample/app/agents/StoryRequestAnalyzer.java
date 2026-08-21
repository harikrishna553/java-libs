package com.sample.app.agents;

import com.sample.app.model.StoryRequest;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryRequestAnalyzer {

  @UserMessage(
      """
			Analyze the following user request and extract information needed
			to create a story.

			Extract:
			- topic: The main subject of the story.
			- style: The desired writing style, such as fantasy, funny,
			  adventurous, educational, etc.
			- audience: The intended audience, such as children, adults,
			  teenagers, etc.

			If you cannot determine a value from the user request, return
			null for that field.

			Do not guess or invent missing information.

			User request:
			'{{request}}'
			""")
  @Agent("Analyzes the user's story request and extracts topic, style and audience")
  StoryRequest analyze(@V("request") String request);
}
