package com.sample.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.sample.app.agents.CreditAgent;
import com.sample.app.agents.DebitAgent;
import com.sample.app.agents.NotificationAgent;
import com.sample.app.agents.TransactionAgent;
import com.sample.app.models.Transaction;
import com.sample.app.tools.BankTool;
import com.sample.app.tools.NotificationTool;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.AgenticServices.AgenticScopeAction;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.ResultWithAgenticScope;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class App {
  private static String readInput(Scanner scanner) {
    System.out.print("> Give me the details to transfer the Money\n");

    String input = scanner.nextLine().trim();

    if ("exit".equalsIgnoreCase(input)) {
      System.exit(0);
    }
    return input;
  }

  public static void main(String[] args) {

    // -------------------------------------------------
    // Parse command-line arguments
    //
    // Usage:
    // java -jar app.jar → simulateFailure = false
    // java -jar app.jar --simulate-failure → simulateFailure = true
    // java -jar app.jar true → simulateFailure = true
    // -------------------------------------------------

    boolean simulateFailure = parseSimulateFailure(args);

    ChatModel chatModel =
        OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3.2")
            .temperature(0.0)
            .build();

    BankTool bankTool = new BankTool();
    NotificationTool notificationTool = new NotificationTool();
    notificationTool.setSimulateFailure(simulateFailure);

    TransactionAgent transactionAgent =
        AgenticServices.agentBuilder(TransactionAgent.class)
            .chatModel(chatModel)
            .outputKey("transactionDetails")
            .build();

    AgenticScopeAction extractTransactionDetails =
        AgenticServices.agentAction(
            scope -> {
              Transaction transaction = (Transaction) scope.readState("transactionDetails");

              if (transaction == null) {
                throw new RuntimeException(
                    "Transaction details are missing. "
                        + "Source account, destination account, and amount are required.");
              }

              if (transaction.getSourceAccount() == null
                  || transaction.getDestinationAccount() == null
                  || transaction.getAmount() == null) {

                StringBuilder message =
                    new StringBuilder(
                        "Transaction cannot be performed. Missing required details: ");

                if (transaction.getSourceAccount() == null) {
                  message.append("source account, ");
                }

                if (transaction.getDestinationAccount() == null) {
                  message.append("destination account, ");
                }

                if (transaction.getAmount() == null) {
                  message.append("amount, ");
                }

                // Remove the trailing comma and space.
                message.setLength(message.length() - 2);

                throw new RuntimeException(message.toString());
              }

              scope.writeState("sourceAccount", transaction.getSourceAccount());
              scope.writeState("destinationAccount", transaction.getDestinationAccount());
              scope.writeState("amount", transaction.getAmount());

              System.out.println("Deduced following data : \n" + transaction);
            });

    CreditAgent creditAgent =
        AgenticServices.agentBuilder(CreditAgent.class)
            .chatModel(chatModel)
            .tools(bankTool)
            .outputKey("creditResult")
            .build();

    DebitAgent debitAgent =
        AgenticServices.agentBuilder(DebitAgent.class)
            .chatModel(chatModel)
            .tools(bankTool)
            .outputKey("debitResult")
            .build();

    NotificationAgent notificationAgent =
        AgenticServices.agentBuilder(NotificationAgent.class)
            .chatModel(chatModel)
            .tools(notificationTool)
            .toolExecutionErrorHandler(
                (error, errorContext) -> {
                  System.out.println();
                  System.out.println("[NOTIFICATION AGENT] Tool execution failed");
                  System.out.println("[NOTIFICATION AGENT] Error: " + error.getMessage());
                  System.out.println("[NOTIFICATION AGENT] Propagating failure...");
                  System.out.println();

                  throw new RuntimeException("Notification failed: " + error.getMessage(), error);
                })
            .outputKey("notificationResult")
            .build();

    UntypedAgent workflow =
        AgenticServices.sequenceBuilder()
            .subAgents(
                transactionAgent,
                extractTransactionDetails,
                creditAgent,
                debitAgent,
                notificationAgent)

            // .listener(new ConsoleAgentListener())
            .compensateOnError(true)
            .outputKey("notificationResult")
            .build();

    try (Scanner scanner = new Scanner(System.in)) {

      while (true) {

        try {
          Map<String, Object> input = new HashMap<>();
          input.put("request", readInput(scanner));

          ResultWithAgenticScope<String> result = workflow.invokeWithAgenticScope(input);

          System.out.println(result.result());
          System.out.println("Workflow completed successfully.");
        } catch (Exception e) {

          System.out.println();
          System.out.println("----------------------------------------------");
          System.out.println("WORKFLOW FAILED");
          System.out.println("----------------------------------------------");

          System.out.println("Reason: " + getRootCauseMessage(e));
        }
      }
    }
  }

  private static String getRootCauseMessage(Throwable throwable) {

    Throwable root = throwable;

    while (root.getCause() != null) {
      root = root.getCause();
    }

    return root.getMessage();
  }

  private static boolean parseSimulateFailure(String[] args) {
    if (args == null || args.length == 0) {
      return false;
    }

    for (String arg : args) {
      if ("--simulate-failure".equalsIgnoreCase(arg)) {
        return true;
      }
      if ("true".equalsIgnoreCase(arg)) {
        return true;
      }
    }
    return false;
  }
}
