package com.sample.app;

import com.sample.app.agents.DependencyAgent;
import com.sample.app.agents.DeploymentAgent;
import com.sample.app.agents.LogAnalysisAgent;
import com.sample.app.agents.MetricsAgent;
import com.sample.app.agents.RemediationAgent;
import com.sample.app.models.Models;
import com.sample.app.tools.DependencyTool;
import com.sample.app.tools.DeploymentTool;
import com.sample.app.tools.LogTool;
import com.sample.app.tools.MonitoringTool;
import com.sample.app.tools.RunbookTool;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorContextStrategy;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    ChatModel baseModel = Models.baseModel();
    ChatModel plannerModel = Models.plannerModel();

    /*
     * ------------------------------------------------
     * Metrics Agent
     * ------------------------------------------------
     */

    MetricsAgent metricsAgent =
        AgenticServices.agentBuilder(MetricsAgent.class)
            .chatModel(baseModel)
            .tools(new MonitoringTool())
            .build();

    /*
     * ------------------------------------------------
     * Log Analysis Agent
     * ------------------------------------------------
     */

    LogAnalysisAgent logAnalysisAgent =
        AgenticServices.agentBuilder(LogAnalysisAgent.class)
            .chatModel(baseModel)
            .tools(new LogTool())
            .build();

    /*
     * ------------------------------------------------
     * Deployment Agent
     * ------------------------------------------------
     */

    DeploymentAgent deploymentAgent =
        AgenticServices.agentBuilder(DeploymentAgent.class)
            .chatModel(baseModel)
            .tools(new DeploymentTool())
            .build();

    /*
     * ------------------------------------------------
     * Dependency Agent
     * ------------------------------------------------
     */

    DependencyAgent dependencyAgent =
        AgenticServices.agentBuilder(DependencyAgent.class)
            .chatModel(baseModel)
            .tools(new DependencyTool())
            .build();

    /*
     * ------------------------------------------------
     * Remediation Agent
     * ------------------------------------------------
     */

    RemediationAgent remediationAgent =
        AgenticServices.agentBuilder(RemediationAgent.class)
            .chatModel(baseModel)
            .tools(new RunbookTool())
            .build();

    /*
     * ------------------------------------------------
     * Supervisor Configuration
     * ------------------------------------------------
     */

    SupervisorResponseStrategy responseStrategy = selectResponseStrategy(scanner);

    SupervisorContextStrategy contextStrategy = selectContextStrategy(scanner);

    /*
     * ------------------------------------------------
     * Supervisor
     * ------------------------------------------------
     *
     * We DON'T define:
     *
     * DeploymentAgent
     *      ↓
     * MetricsAgent
     *      ↓
     * LogAnalysisAgent
     *      ↓
     * DependencyAgent
     *      ↓
     * RemediationAgent
     *
     * The Supervisor decides the execution path.
     */

    SupervisorAgent incidentSupervisor =
        AgenticServices.supervisorBuilder()
            .chatModel(plannerModel)
            .subAgents(
                metricsAgent, logAnalysisAgent, deploymentAgent, dependencyAgent, remediationAgent)
            .supervisorContext(
                """
                                You are coordinating a production
                                incident investigation.

                                Your objective is to determine the
                                most probable root cause and recommend
                                the safest remediation.

                                Rules:

                                1. Dynamically select specialist agents
                                   based on the incident and evidence.

                                2. Do not invoke every agent automatically.

                                3. Examine the result of each agent before
                                   deciding what to do next.

                                4. Gather enough evidence to distinguish
                                   correlation from causation.

                                5. Prefer factual evidence obtained from
                                   tools over assumptions.

                                6. Use the RemediationAgent only after
                                   sufficient evidence has been collected.

                                7. Never claim that an operational action
                                   was actually executed.

                                8. Stop when the incident has been
                                   sufficiently investigated.
                                """)
            .responseStrategy(responseStrategy)
            .contextGenerationStrategy(contextStrategy)
            .build();

    /*
     * ------------------------------------------------
     * Print Selected Configuration
     * ------------------------------------------------
     */

    System.out.println(
        """

                ==========================================
                SUPERVISOR CONFIGURATION
                ==========================================
                """);

    System.out.println("Response Strategy : " + responseStrategy);
    System.out.println("Context Strategy  : " + contextStrategy);

    /*
     * ------------------------------------------------
     * User Request
     * ------------------------------------------------
     */

    String request =
        """
                The checkout-service started returning
                HTTP 500 errors immediately after
                today's deployment.

                Investigate the incident,
                identify the most probable root cause,
                and recommend the safest next action.
                """;

    System.out.println(
        """

                ==========================================
                USER REQUEST
                ==========================================
                """);

    System.out.println(request);

    /*
     * ------------------------------------------------
     * Invoke Supervisor
     * ------------------------------------------------
     */

    String response = incidentSupervisor.invoke(request);

    System.out.println(
        """

                ==========================================
                SUPERVISOR RESPONSE
                ==========================================
                """);

    System.out.println(response);

    scanner.close();
  }

  /*
   * ------------------------------------------------
   * Supervisor Response Strategy Selection
   * ------------------------------------------------
   */

  private static SupervisorResponseStrategy selectResponseStrategy(Scanner scanner) {

    SupervisorResponseStrategy[] strategies = SupervisorResponseStrategy.values();

    System.out.println(
        """

                ==========================================
                SUPERVISOR RESPONSE STRATEGY
                ==========================================

                Select how the final response should be returned:
                """);

    for (int i = 0; i < strategies.length; i++) {
      System.out.printf("%d. %s%n", i + 1, strategies[i]);
    }

    int selection = readSelection(scanner, strategies.length);

    SupervisorResponseStrategy selected = strategies[selection - 1];

    System.out.println();
    System.out.println("Selected Response Strategy: " + selected);

    return selected;
  }

  /*
   * ------------------------------------------------
   * Supervisor Context Strategy Selection
   * ------------------------------------------------
   */

  private static SupervisorContextStrategy selectContextStrategy(Scanner scanner) {

    SupervisorContextStrategy[] strategies = SupervisorContextStrategy.values();

    System.out.println(
        """

                ==========================================
                SUPERVISOR CONTEXT STRATEGY
                ==========================================

                Select how Supervisor context should be generated:
                """);

    for (int i = 0; i < strategies.length; i++) {
      System.out.printf("%d. %s%n", i + 1, strategies[i]);
    }

    int selection = readSelection(scanner, strategies.length);

    SupervisorContextStrategy selected = strategies[selection - 1];

    System.out.println();
    System.out.println("Selected Context Strategy: " + selected);

    return selected;
  }

  /*
   * ------------------------------------------------
   * Generic Console Selection
   * ------------------------------------------------
   */

  private static int readSelection(Scanner scanner, int numberOfOptions) {

    while (true) {

      System.out.printf("Enter your choice [1-%d]: ", numberOfOptions);

      String input = scanner.nextLine();

      try {

        int selection = Integer.parseInt(input);

        if (selection >= 1 && selection <= numberOfOptions) {

          return selection;
        }

      } catch (NumberFormatException ignored) {
        // handled below
      }

      System.out.println("Invalid selection. Please try again.");
    }
  }
}
