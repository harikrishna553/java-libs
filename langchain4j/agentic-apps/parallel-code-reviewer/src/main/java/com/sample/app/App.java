package com.sample.app;

import com.sample.app.agents.AgentWorkflowListener;
import com.sample.app.agents.CodeQualityExpert;
import com.sample.app.agents.CodeReviewExpert;
import com.sample.app.agents.PerformanceExpert;
import com.sample.app.agents.RefactoringExpert;
import com.sample.app.agents.SecurityExpert;
import com.sample.app.config.OllamaConfig;
import com.sample.app.model.CodeReviewComments;
import com.sample.app.model.RefactoringSuggestion;
import com.sample.app.model.RefactoringSuggestions;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class App {

  public static String readFileToString(String codeFilePath) throws IOException {
    if (codeFilePath == null || codeFilePath.isEmpty()) {
      System.out.println("Code File path should not be empty");
      System.exit(1);
    }

    return Files.readString(Path.of(codeFilePath));
  }

  public static void main(String[] args) throws IOException {
    System.out.println("Enter the code file path to review the code");

    String codeToReview = null;

    try (Scanner scanner = new Scanner(System.in)) {
      String codeFilePath = scanner.nextLine().trim();
      codeToReview = readFileToString(codeFilePath);
    }

    ChatModel chatModel = OllamaConfig.createChatModel();

    // Define Agents
    CodeQualityExpert codeQualityExpert =
        AgenticServices.agentBuilder(CodeQualityExpert.class)
            .chatModel(chatModel)
            .outputKey("codeQualityComments")
            .build();

    SecurityExpert securityExpert =
        AgenticServices.agentBuilder(SecurityExpert.class)
            .chatModel(chatModel)
            .outputKey("codeSecurityComments")
            .build();

    PerformanceExpert performanceExpert =
        AgenticServices.agentBuilder(PerformanceExpert.class)
            .chatModel(chatModel)
            .outputKey("codePerformanceComments")
            .build();

    RefactoringExpert refactoringExpert =
        AgenticServices.agentBuilder(RefactoringExpert.class)
            .chatModel(chatModel)
            .outputKey("refactoringSuggestions")
            .build();

    CodeReviewExpert codeReviewExpert =
        AgenticServices.parallelBuilder(CodeReviewExpert.class)
            .subAgents(codeQualityExpert, securityExpert, performanceExpert, refactoringExpert)
            .listener(new AgentWorkflowListener())
            .executor(Executors.newFixedThreadPool(4))
            .outputKey("plans")
            .output(
                agenticScope -> {
                  List<String> codeQualityComments =
                      agenticScope.readState("codeQualityComments", List.of());
                  List<String> codeSecurityComments =
                      agenticScope.readState("codeSecurityComments", List.of());
                  List<String> codePerformanceComments =
                      agenticScope.readState("codePerformanceComments", List.of());
                  RefactoringSuggestions refactoringState =
                      agenticScope.readState("refactoringSuggestions", null);
                  List<RefactoringSuggestion> refactoringSuggestions =
                      refactoringState != null && refactoringState.getSuggestions() != null
                          ? refactoringState.getSuggestions()
                          : List.of();

                  CodeReviewComments codeReviewComments = new CodeReviewComments();
                  codeReviewComments.setCodePerformanceComments(codePerformanceComments);
                  codeReviewComments.setCodeQualityComments(codeQualityComments);
                  codeReviewComments.setCodeSecurityComments(codeSecurityComments);
                  codeReviewComments.setRefactoringSuggestions(refactoringSuggestions);

                  return codeReviewComments;
                })
            .build();

    CodeReviewComments codeReviewComments = codeReviewExpert.reviewCode(codeToReview);

    System.out.println(codeReviewComments);
    System.exit(0);
  }
}
