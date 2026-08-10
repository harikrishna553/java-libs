package com.sample.app;

import com.sample.app.agents.AgentWorkflowFactory;
import com.sample.app.console.ConsoleRenderer;
import com.sample.app.model.OllamaConfig;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.ResultWithAgenticScope;
import dev.langchain4j.model.chat.ChatModel;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    ConsoleRenderer.printBanner();

    Scanner scanner = new Scanner(System.in);

    ConsoleRenderer.info("Enter your story idea:");
    ConsoleRenderer.printPrompt();

    String storyIdea = scanner.nextLine().trim();

    if (storyIdea.isEmpty()) {
      ConsoleRenderer.error("A story idea is required to run the workflow.");
      return;
    }

    ChatModel chatModel = OllamaConfig.createChatModel();

    UntypedAgent workflow = AgentWorkflowFactory.createWorkflow(chatModel);

    Map<String, Object> input = new LinkedHashMap<>();
    input.put("storyIdea", storyIdea);

    try {
      ResultWithAgenticScope<String> result = workflow.invokeWithAgenticScope(input);

      ConsoleRenderer.info("Workflow completed successfully.");

      ConsoleRenderer.agent(result.result());

    } catch (Exception exception) {
      handleWorkflowFailure(exception);
    }
  }

  private static void handleWorkflowFailure(Exception exception) {

    String baseUrl = OllamaConfig.resolveBaseUrl();

    ConsoleRenderer.error("Workflow execution failed.");

    ConsoleRenderer.info("Unable to connect to Ollama at " + baseUrl + ".");

    ConsoleRenderer.info("Please make sure Ollama is running and the model is available.");

    if (exception.getMessage() != null && !exception.getMessage().isBlank()) {

      ConsoleRenderer.info("Technical details: " + exception.getMessage());
    }
  }
}
