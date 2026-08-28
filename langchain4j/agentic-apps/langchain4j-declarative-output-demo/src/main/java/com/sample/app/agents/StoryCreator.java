package com.sample.app.agents;

import com.sample.app.config.OllamaConfig;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryCreator {

    @UserMessage("""
        Create a short children's story based on the given idea.

        Requirements:
        - Use simple language for children ages 5 to 10.
        - Include a beginning, middle, and ending.
        - Include a positive message.
        - Give the story a suitable title.

        Story idea:
        {{storyIdea}}

        Return only the story.
        """)
    @Agent(
        name = "storyCreator",
        description = "Creates the initial children's story.",
        outputKey = "originalStory"
    )
    String createStory(@V("storyIdea") String storyIdea);

    @ChatModelSupplier
    static ChatModel chatModel() {
		return OllamaConfig.getChatModel();
    }
}