package com.sample.app;

import com.sample.app.agents.DeploymentLookupAgent;
import com.sample.app.agents.IncidentAnalyzerAgent;
import com.sample.app.agents.PriorityCalculatorAgent;
import com.sample.app.agents.RootCauseAnalyzerAgent;
import com.sample.app.agents.ServiceHealthAgent;
import com.sample.app.agents.TicketCreationAgent;
import com.sample.app.chatmodels.Models;
import com.sample.app.model.IncidentTicket;
import com.sample.app.service.DeploymentService;
import com.sample.app.service.MonitoringService;
import com.sample.app.service.TicketService;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Map;

public class App {

  public static void main(String[] args) {

    printHeader();

    ChatModel model = Models.baseModel();

    /*
     * -------------------------------------------------- 2. AI AGENTS
     * --------------------------------------------------
     */

    IncidentAnalyzerAgent incidentAnalyzer =
        AgenticServices.agentBuilder(IncidentAnalyzerAgent.class).chatModel(model).build();

    RootCauseAnalyzerAgent rootCauseAnalyzer =
        AgenticServices.agentBuilder(RootCauseAnalyzerAgent.class).chatModel(model).build();

    /*
     * -------------------------------------------------- 3. Enterprise services
     *
     * These could later be REST clients, repositories, Jira clients, ServiceNow
     * clients, etc. --------------------------------------------------
     */

    MonitoringService monitoringService = new MonitoringService();

    DeploymentService deploymentService = new DeploymentService();

    TicketService ticketService = new TicketService();

    /*
     * -------------------------------------------------- 4. NON-AI AGENTS
     * --------------------------------------------------
     */

    ServiceHealthAgent serviceHealthAgent = new ServiceHealthAgent(monitoringService);

    DeploymentLookupAgent deploymentLookupAgent = new DeploymentLookupAgent(deploymentService);

    PriorityCalculatorAgent priorityCalculatorAgent = new PriorityCalculatorAgent();

    TicketCreationAgent ticketCreationAgent = new TicketCreationAgent(ticketService);

    /*
     * -------------------------------------------------- 5. Build hybrid agentic
     * workflow --------------------------------------------------
     *
     * AI ↓ agentAction ↓ Non-AI ↓ Non-AI ↓ AI ↓ Non-AI ↓ Non-AI
     */

    UntypedAgent incidentResolutionWorkflow =
        AgenticServices.sequenceBuilder()
            .subAgents(

                /*
                 * AI
                 *
                 * incident → serviceName
                 */
                incidentAnalyzer,

                /*
                 * Tiny deterministic agent.
                 *
                 * Manipulates AgenticScope.
                 *
                 * Demonstrates agentAction().
                 */
                AgenticServices.agentAction(
                    scope -> {
                      String serviceName = scope.readState("serviceName", "");

                      String normalized = serviceName.trim().toLowerCase();

                      System.out.println();
                      System.out.println(">>> agentAction");

                      System.out.println(
                          "Normalizing serviceName: " + serviceName + " -> " + normalized);

                      scope.writeState("serviceName", normalized);
                    }),

                /*
                 * NON-AI
                 *
                 * serviceName → serviceHealth
                 */
                serviceHealthAgent,

                /*
                 * NON-AI
                 *
                 * serviceName → deploymentInfo
                 */
                deploymentLookupAgent,

                /*
                 * AI
                 *
                 * incident serviceName serviceHealth deploymentInfo
                 *
                 * →
                 *
                 * rootCause
                 */
                rootCauseAnalyzer,

                /*
                 * NON-AI
                 *
                 * serviceHealth → priority
                 */
                priorityCalculatorAgent,

                /*
                 * NON-AI
                 *
                 * Everything collected above → IncidentTicket
                 */
                ticketCreationAgent)
            .outputKey("ticket")
            .build();

    /*
     * -------------------------------------------------- 6. User incident
     * --------------------------------------------------
     */

    String incident =
        """
				Customers are reporting intermittent HTTP 500
				errors while placing orders through the
				checkout service.

				The issue started shortly after today's
				application deployment.

				Users can browse products normally, but some
				checkout requests are failing.
				""";

    System.out.println();
    System.out.println("USER INCIDENT");
    System.out.println("=============");
    System.out.println(incident);

    /*
     * -------------------------------------------------- 7. Execute agentic
     * workflow --------------------------------------------------
     */

    IncidentTicket ticket =
        (IncidentTicket) incidentResolutionWorkflow.invoke(Map.of("incident", incident));

    /*
     * -------------------------------------------------- 8. Result
     * --------------------------------------------------
     */

    printTicket(ticket);
  }

  private static void printHeader() {

    System.out.println(
        """

				============================================
				LANGCHAIN4J HYBRID AGENTIC SYSTEM
				AI Agents + Non-AI Agents
				============================================
				""");
  }

  private static void printTicket(IncidentTicket ticket) {

    System.out.println();
    System.out.println("============================================");

    System.out.println("FINAL INCIDENT TICKET");

    System.out.println("============================================");

    System.out.println("Ticket ID      : " + ticket.getTicketId());

    System.out.println("Service        : " + ticket.getServiceName());

    System.out.println("Priority       : " + ticket.getPriority());

    System.out.println("Status         : " + ticket.getServiceHealth().getStatus());

    System.out.println("Error Rate     : " + ticket.getServiceHealth().getErrorRate() + "%");

    System.out.println("Latency        : " + ticket.getServiceHealth().getLatencyMs() + " ms");

    System.out.println("Version        : " + ticket.getDeploymentInfo().getVersion());

    System.out.println("Previous       : " + ticket.getDeploymentInfo().getPreviousVersion());

    System.out.println();
    System.out.println("ROOT CAUSE ANALYSIS");

    System.out.println("--------------------------------------------");

    System.out.println(ticket.getRootCauseAnalysis());

    System.out.println();
    System.out.println("============================================");
  }
}
