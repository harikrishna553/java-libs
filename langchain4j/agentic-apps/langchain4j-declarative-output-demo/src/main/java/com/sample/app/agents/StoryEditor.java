package com.sample.app.agents;

import com.sample.app.config.OllamaConfig;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryEditor {

	@UserMessage("""
			Review and improve the following children's story.

			Improve:
			- clarity
			- grammar
			- flow
			- engagement
			- age-appropriate language

			Keep the original idea and positive message.

			Story:
			{{originalStory}}

			Strictly return only the improved story.
			""")
	@Agent(name = "storyEditor", description = "Reviews and improves the story created by StoryCreator.", outputKey = "editedStory")
	String editStory(@V("originalStory") String originalStory);

	@ChatModelSupplier
	static ChatModel chatModel() {
		return OllamaConfig.getChatModel();
	}
}