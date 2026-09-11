package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryReviewerAgent {

    @Agent(
        name = "storyReviewer",
        description = "Reviews a story and provides actionable improvement feedback"
    )
    @UserMessage("""
        You are an experienced story reviewer.

        Review the following story.

        ORIGINAL STORY IDEA:
        {{storyIdea}}

        STORY DRAFT:
        {{storyDraft}}

        Evaluate the story for:

        1. Alignment with the original idea
        2. Plot consistency
        3. Character development
        4. Story structure
        5. Readability
        6. Grammar
        7. Engagement
        8. Quality of the ending

        Identify strengths as well as areas that should be improved.

        Provide clear and actionable feedback that another editor
        can use to improve the story.

        Do not rewrite the story.

        Return only the review feedback.
        """)
    String reviewStory(
        @V("storyIdea") String storyIdea,
        @V("storyDraft") String storyDraft
    );
}