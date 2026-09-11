package com.sample.app;

import com.sample.app.agents.StoryEditorAgent;
import com.sample.app.agents.StoryReviewerAgent;
import com.sample.app.agents.StoryWriterAgent;
import com.sample.app.chatmodels.Models;
import com.sample.app.planner.CustomSequentialPlanner;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;

import java.util.Map;

public class App {

    public static void main(String[] args) {

        /*
         * ---------------------------------------------------------
         * Chat Model
         * ---------------------------------------------------------
         */
		ChatModel chatModel = Models.baseModel();

        /*
         * ---------------------------------------------------------
         * Story Writer Agent
         * ---------------------------------------------------------
         *
         * Input:
         *   storyIdea
         *
         * Output:
         *   storyDraft
         */
        StoryWriterAgent storyWriter =
            AgenticServices
                .agentBuilder(StoryWriterAgent.class)
                .chatModel(chatModel)
                .outputKey("storyDraft")
                .build();

        /*
         * ---------------------------------------------------------
         * Story Reviewer Agent
         * ---------------------------------------------------------
         *
         * Inputs:
         *   storyIdea
         *   storyDraft
         *
         * Output:
         *   reviewFeedback
         */
        StoryReviewerAgent storyReviewer =
            AgenticServices
                .agentBuilder(StoryReviewerAgent.class)
                .chatModel(chatModel)
                .outputKey("reviewFeedback")
                .build();

        /*
         * ---------------------------------------------------------
         * Story Editor Agent
         * ---------------------------------------------------------
         *
         * Inputs:
         *   storyIdea
         *   storyDraft
         *   reviewFeedback
         *
         * Output:
         *   story
         */
        StoryEditorAgent storyEditor =
            AgenticServices
                .agentBuilder(StoryEditorAgent.class)
                .chatModel(chatModel)
                .outputKey("story")
                .build();

        /*
         * ---------------------------------------------------------
         * Custom Agentic Workflow
         * ---------------------------------------------------------
         *
         * We are NOT using:
         *
         * AgenticServices.sequenceBuilder()
         *
         * Instead, we use plannerBuilder() and supply our
         * CustomSequentialPlanner.
         */
        UntypedAgent storyWorkflow =
            AgenticServices
                .plannerBuilder()

                /*
                 * The order is important because our planner
                 * processes the configured agents sequentially.
                 */
                .subAgents(
                    storyWriter,
                    storyReviewer,
                    storyEditor
                )

                /*
                 * Final workflow result is taken from the
                 * AgenticScope variable named "story".
                 */
                .outputKey("story")

                /*
                 * A new CustomSequentialPlanner controls
                 * the execution path.
                 */
                .planner(CustomSequentialPlanner::new)

                .build();

        /*
         * ---------------------------------------------------------
         * User Input
         * ---------------------------------------------------------
         */
        Map<String, Object> input = Map.of(
            "storyIdea",
            """
            A young explorer discovers a hidden city beneath
            a mountain where an ancient civilization has been
            living in isolation for hundreds of years.
            """
        );

        /*
         * ---------------------------------------------------------
         * Execute Workflow
         * ---------------------------------------------------------
         */
        String finalStory =
            (String) storyWorkflow.invoke(input);

        /*
         * ---------------------------------------------------------
         * Result
         * ---------------------------------------------------------
         */
        System.out.println();
        System.out.println("======================================");
        System.out.println("FINAL STORY");
        System.out.println("======================================");
        System.out.println();

        System.out.println(finalStory);
    }
}