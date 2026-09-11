package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryEditorAgent {

    @Agent(
        name = "storyEditor",
        description = "Edits a story using reviewer feedback and produces the final story"
    )
    @UserMessage("""
        You are a professional story editor.

        Improve the story using the review feedback.

        ORIGINAL STORY IDEA:
        {{storyIdea}}

        ORIGINAL STORY DRAFT:
        {{storyDraft}}

        REVIEW FEEDBACK:
        {{reviewFeedback}}

        Rewrite the story so that:

        - Important reviewer feedback is addressed.
        - The original story idea is preserved.
        - Plot inconsistencies are corrected.
        - Character behavior remains believable.
        - The story flows naturally.
        - Grammar and readability are improved.
        - The ending is satisfying.
        - Unnecessary repetition is removed.

        Return only the final improved story.

        Do not include comments, review notes, or explanations.
        """)
    String editStory(
        @V("storyIdea") String storyIdea,
        @V("storyDraft") String storyDraft,
        @V("reviewFeedback") String reviewFeedback
    );
}