package com.sample.app;

import com.sample.app.agents.AgentWorkflowListener;
import com.sample.app.agents.CharacterDesigner;
import com.sample.app.agents.StoryEditor;
import com.sample.app.agents.StoryPlanner;
import com.sample.app.agents.StoryWriter;
import com.sample.app.config.OllamaConfig;
import com.sample.app.console.ConsoleRenderer;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.ResultWithAgenticScope;
import dev.langchain4j.model.chat.ChatModel;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public final class App {
  private static void handleWorkflowFailure(Exception exception) {

    String baseUrl = OllamaConfig.resolveBaseUrl();

    ConsoleRenderer.error("Workflow execution failed.");

    ConsoleRenderer.info("Unable to connect to Ollama at " + baseUrl + ".");

    ConsoleRenderer.info("Please make sure Ollama is running and the model is available.");

    if (exception.getMessage() != null && !exception.getMessage().isBlank()) {

      ConsoleRenderer.info("Technical details: " + exception.getMessage());
    }
  }

  public static void main(String[] args) {

    ChatModel chatModel = OllamaConfig.createChatModel();

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
    UntypedAgent workflow =
        AgenticServices.sequenceBuilder()
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

    ConsoleRenderer.printBanner();

    try (Scanner scanner = new Scanner(System.in)) {
      ConsoleRenderer.info("Enter your story idea:");
      ConsoleRenderer.printPrompt();

      String storyIdea = scanner.nextLine().trim();

      if (storyIdea.isEmpty()) {
        ConsoleRenderer.error("A story idea is required to run the workflow.");
        return;
      }

      Map<String, Object> input = new LinkedHashMap<>();
      input.put("storyIdea", storyIdea);

      try {
        ResultWithAgenticScope<String> result = workflow.invokeWithAgenticScope(input);

        ConsoleRenderer.info("Workflow completed successfully.");

        ConsoleRenderer.agent(result.agenticScope().readState("finalStory").toString());

      } catch (Exception exception) {
        handleWorkflowFailure(exception);
      }
    }
  }
}
