package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryWriterAgent {

    @Agent(
        name = "storyWriter",
        description = "Creates the initial story from the user's story idea"
    )
    @UserMessage("""
        You are a creative story writer.

        Write an engaging short story based on the following story idea.

        STORY IDEA:
        {{storyIdea}}

        Requirements:
        - Create an interesting beginning, middle, and ending.
        - Introduce believable characters.
        - Keep the plot consistent.
        - Make the story engaging and easy to follow.
        - Do not provide analysis or explanation.

        Return only the story.
        """)
    String writeStory(@V("storyIdea") String storyIdea);
}