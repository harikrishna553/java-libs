package com.sample.app.agents;

import com.sample.app.model.StoryResult;

import dev.langchain4j.agentic.declarative.Output;
import dev.langchain4j.agentic.declarative.SequenceAgent;
import dev.langchain4j.service.V;

public interface StoryWorkflow {

    @SequenceAgent(
        name = "storyWorkflow",
        subAgents = {
            StoryCreator.class,
            StoryEditor.class
        }
    )
    StoryResult createStory(@V("storyIdea") String storyIdea);

    @Output
    static StoryResult createOutput(
        @V("originalStory") String originalStory,
        @V("editedStory") String editedStory
    ) {
        return new StoryResult(
            originalStory,
            editedStory
        );
    }
}