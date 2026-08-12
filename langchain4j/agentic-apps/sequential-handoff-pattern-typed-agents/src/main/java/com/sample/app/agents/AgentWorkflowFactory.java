package com.sample.app.agents;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;

public final class AgentWorkflowFactory {

  private AgentWorkflowFactory() {}

  public static UntypedAgent createWorkflow(ChatModel chatModel) {

    StoryPlanner storyPlanner =
        AgenticServices.agentBuilder(StoryPlanner.class)
            .chatModel(chatModel)
            .outputKey("plot")
            .build();

    CharacterDesigner characterDesigner =
        AgenticServices.agentBuilder(CharacterDesigner.class)
            .chatModel(chatModel)
            .outputKey("characters")
            .build();

    StoryWriter storyWriter =
        AgenticServices.agentBuilder(StoryWriter.class)
            .chatModel(chatModel)
            .outputKey("draft")
            .build();

    StoryEditor storyEditor =
        AgenticServices.agentBuilder(StoryEditor.class)
            .chatModel(chatModel)
            .outputKey("story")
            .build();

    // ------------------------------------------------------------
    // Sequential Handoff Workflow
    // ------------------------------------------------------------
    return AgenticServices.sequenceBuilder()
        .name("sequentialStoryWorkflow")
        .description(
            """
				Creates a children's story through a deterministic sequence
				of specialized agents.

				Each agent completes one stage of the story creation process
				and hands its output to the next agent:

				Story Planner
				    ->
				Character Designer
				    ->
				Story Writer
				    ->
				Story Editor

				The workflow starts with a simple story idea and progressively
				transforms it into a polished final story.
				""")
        .subAgents(
            // Handoff 1: Story Planner -> Character Designer
            storyPlanner,

            // Handoff 2: Character Designer -> Story Writer
            characterDesigner,

            // Handoff 3: Story Writer -> Story Editor
            storyWriter,
            storyEditor)
        .listener(new AgentWorkflowListener())
        .outputKey("finalStory")
        .build();
  }
}
