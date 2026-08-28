package com.sample.app;

import com.sample.app.agents.StoryWorkflow;
import com.sample.app.config.OllamaConfig;
import com.sample.app.console.ConsoleRenderer;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.observability.AgentMonitor;
import dev.langchain4j.agentic.observability.HtmlReportGenerator;
import dev.langchain4j.agentic.observability.MonitoredExecution;
import java.nio.file.Path;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    ConsoleRenderer.printBanner();
    System.out.println("Enter Your Story Idea");
    String storyIdea = null;
    try (Scanner scanner = new Scanner(System.in)) {
      storyIdea = scanner.nextLine().trim();
    }

    if (storyIdea.isEmpty()) {
      ConsoleRenderer.error("A story idea is required to run the workflow.");
      return;
    }

    StoryWorkflow storyWorkflow =
        AgenticServices.createAgenticSystem(StoryWorkflow.class, OllamaConfig.getChatModel());

    String finalStory = storyWorkflow.createStory(storyIdea);

    ConsoleRenderer.finalResult("Final Story");
    ConsoleRenderer.finalResult(finalStory);

    AgentMonitor agentMonitor = storyWorkflow.agentMonitor();

    MonitoredExecution successfulExecution = agentMonitor.successfulExecutions().get(0);
    System.out.println(successfulExecution);

    HtmlReportGenerator.generateReport(agentMonitor, Path.of("review-loop.html"));
    HtmlReportGenerator.generateExecution(agentMonitor, Path.of("execution.html"));
  }
}
