package com.sample.app;

import com.sample.app.agents.AgentWorkflowListener;
import com.sample.app.agents.StoryImprover;
import com.sample.app.agents.StoryReviewer;
import com.sample.app.agents.StoryWriter;
import com.sample.app.config.OllamaConfig;
import com.sample.app.console.ConsoleRenderer;
import com.sample.app.model.StoryReview;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.ResultWithAgenticScope;
import dev.langchain4j.model.chat.ChatModel;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    ConsoleRenderer.printBanner();
    System.out.println("Enter Your Story Idea");
    Scanner scanner = new Scanner(System.in);
    String storyIdea = scanner.nextLine().trim();

    if (storyIdea.isEmpty()) {
      ConsoleRenderer.error("A story idea is required to run the workflow.");
      return;
    }

    ChatModel chatModel = OllamaConfig.createChatModel();

    ChatModel structuredChatModel = OllamaConfig.createStructuredChatModel();

    // Define Agents
    StoryWriter storyWriter =
        AgenticServices.agentBuilder(StoryWriter.class)
            .chatModel(chatModel)
            .outputKey("story")
            .build();

    StoryReviewer storyReviewer =
        AgenticServices.agentBuilder(StoryReviewer.class)
            .chatModel(structuredChatModel)
            .outputKey("storyReview")
            .build();

    StoryImprover storyImprover =
        AgenticServices.agentBuilder(StoryImprover.class)
            .chatModel(chatModel)
            .outputKey("story")
            .build();

    UntypedAgent storyImprovementLoop =
        AgenticServices.loopBuilder()
            .subAgents(storyReviewer, storyImprover)
            .maxIterations(5)
            .testExitAtLoopEnd(true)
            .exitCondition(
                (agenticScope, loopCounter) -> {
                  StoryReview storyReview = agenticScope.readState("storyReview", null);

                  double score = (storyReview != null) ? storyReview.getRating() : 0;

                  return loopCounter <= 3 ? score >= 9 : score >= 7;
                })
            .outputKey("story")
            .build();

    UntypedAgent storyWorkflow =
        AgenticServices.sequenceBuilder()
            .name("sequentialStoryWorkflow")
            .subAgents(storyWriter, storyImprovementLoop)
            .listener(new AgentWorkflowListener())
            .outputKey("story")
            .build();

    Map<String, Object> input = new LinkedHashMap<>();
    input.put("storyIdea", storyIdea);

    ResultWithAgenticScope<String> result = storyWorkflow.invokeWithAgenticScope(input);

    ConsoleRenderer.finalResult("Final Story");
    ConsoleRenderer.finalResult(result.result());
  }
}
