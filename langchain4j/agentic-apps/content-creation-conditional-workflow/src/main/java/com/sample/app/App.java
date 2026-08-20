package com.sample.app;

import com.sample.app.agents.BlogExpert;
import com.sample.app.agents.ContentCreator;
import com.sample.app.agents.ContentRouter;
import com.sample.app.agents.EmailExpert;
import com.sample.app.agents.SocialMediaExpert;
import com.sample.app.agents.UnknownContentExpert;
import com.sample.app.config.OllamaConfig;
import com.sample.app.enums.ContentType;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Scanner;

public class App {

  private static String readInput(Scanner scanner) {
    System.out.print("> ");
    String input = scanner.nextLine().trim();

    return "exit".equalsIgnoreCase(input) ? null : input;
  }

  public static void main(String[] args) {
    ChatModel chatModel = OllamaConfig.createChatModel();

    ContentRouter routerAgent =
        AgenticServices.agentBuilder(ContentRouter.class)
            .chatModel(chatModel)
            .outputKey("contentType")
            .build();

    BlogExpert blogExpert =
        AgenticServices.agentBuilder(BlogExpert.class)
            .chatModel(chatModel)
            .outputKey("response")
            .build();

    SocialMediaExpert socialMediaExpert =
        AgenticServices.agentBuilder(SocialMediaExpert.class)
            .chatModel(chatModel)
            .outputKey("response")
            .build();

    EmailExpert emailExpert =
        AgenticServices.agentBuilder(EmailExpert.class)
            .chatModel(chatModel)
            .outputKey("response")
            .build();

    UnknownContentExpert unknownContentExpert =
        AgenticServices.agentBuilder(UnknownContentExpert.class)
            .chatModel(chatModel)
            .outputKey("response")
            .build();

    UntypedAgent contentAgent =
        AgenticServices.conditionalBuilder()
            .subAgents(
                scope -> scope.readState("contentType", ContentType.UNKNOWN) == ContentType.BLOG,
                blogExpert)
            .subAgents(
                scope ->
                    scope.readState("contentType", ContentType.UNKNOWN) == ContentType.SOCIAL_MEDIA,
                socialMediaExpert)
            .subAgents(
                scope -> scope.readState("contentType", ContentType.UNKNOWN) == ContentType.EMAIL,
                emailExpert)
            .subAgents(
                scope -> scope.readState("contentType", ContentType.UNKNOWN) == ContentType.UNKNOWN,
                unknownContentExpert)
            .build();

    ContentCreator contentCreator =
        AgenticServices.sequenceBuilder(ContentCreator.class)
            .subAgents(routerAgent, contentAgent)
            .outputKey("response")
            .build();

    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        String input = readInput(scanner);

        if ("exit".equalsIgnoreCase(input)) {
          System.exit(0);
        }

        String response = contentCreator.create(input);
        System.out.println(response);
      }
    }
  }
}
