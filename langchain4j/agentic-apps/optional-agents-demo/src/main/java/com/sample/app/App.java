package com.sample.app;

import com.sample.app.agents.AudienceEditor;
import com.sample.app.agents.CreativeWriter;
import com.sample.app.agents.StoryRequestAnalyzer;
import com.sample.app.agents.StyleEditor;
import com.sample.app.config.OllamaConfig;
import com.sample.app.model.StoryRequest;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.AgenticServices.AgenticScopeAction;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.ResultWithAgenticScope;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Map;
import java.util.Scanner;

public class App {

  private static String readInput(Scanner scanner) {
    System.out.print("> ");
    String input = scanner.nextLine().trim();

    return "exit".equalsIgnoreCase(input) ? null : input;
  }

  public static void main(String[] args) {
    ChatModel chatModel = OllamaConfig.createChatModel();

    StoryRequestAnalyzer storyRequestAnalyzer =
        AgenticServices.agentBuilder(StoryRequestAnalyzer.class)
            .chatModel(chatModel)
            .outputKey("storyRequest")
            .build();

    AgenticScopeAction extractStoryRequest =
        AgenticServices.agentAction(
            scope -> {
              StoryRequest storyRequest = (StoryRequest) scope.readState("storyRequest");

              if (storyRequest.getTopic() == null || storyRequest.getTopic().isBlank()) {
                System.out.println(
                    "Unable to deduce the topic, setting to 'Any Interesting Topic'");
                storyRequest.setTopic("Any Interesting Topic");
              }

              if (storyRequest.getStyle() == null || storyRequest.getStyle().isBlank()) {
                System.out.println("Unable to deduce the style, setting it to 'creative'");
                storyRequest.setStyle("creative");
              }

              scope.writeState("topic", storyRequest.getTopic());
              scope.writeState("style", storyRequest.getStyle());

              if (storyRequest.getAudience() != null && !storyRequest.getAudience().isBlank()) {

                scope.writeState("audience", storyRequest.getAudience());
              }

              System.out.println("Deduced following data : \n" + storyRequest);
            });

    CreativeWriter creativeWriter =
        AgenticServices.agentBuilder(CreativeWriter.class)
            .chatModel(chatModel)
            .outputKey("story")
            .build();

    AudienceEditor audienceEditor =
        AgenticServices.agentBuilder(AudienceEditor.class)
            .chatModel(chatModel)
            .outputKey("story")
            .optional(true)
            .build();

    StyleEditor styleEditor =
        AgenticServices.agentBuilder(StyleEditor.class)
            .chatModel(chatModel)
            .outputKey("story")
            .build();

    UntypedAgent storyWorkflow =
        AgenticServices.sequenceBuilder()
            .subAgents(
                storyRequestAnalyzer,
                extractStoryRequest,
                creativeWriter,
                audienceEditor,
                styleEditor)
            .outputKey("story")
            .build();

    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        String input = readInput(scanner);

        if ("exit".equalsIgnoreCase(input)) {
          System.exit(0);
        }

        ResultWithAgenticScope<String> result =
            storyWorkflow.invokeWithAgenticScope(Map.of("request", input));
        System.out.println(result.result());
      }
    }
  }
}
