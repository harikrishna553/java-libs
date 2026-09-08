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
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.chat.ChatModel;

public class App {

  public static void main(String[] args) {

    ChatModel baseModel = Models.baseModel();

    ChatModel plannerModel = Models.plannerModel();

    /*
     * ------------------------------------------------ Metrics Agent
     * ------------------------------------------------
     */

    MetricsAgent metricsAgent =
        AgenticServices.agentBuilder(MetricsAgent.class)
            .chatModel(baseModel)
            .tools(new MonitoringTool())
            .build();

    /*
     * ------------------------------------------------ Log Analysis Agent
     * ------------------------------------------------
     */

    LogAnalysisAgent logAnalysisAgent =
        AgenticServices.agentBuilder(LogAnalysisAgent.class)
            .chatModel(baseModel)
            .tools(new LogTool())
            .build();

    /*
     * ------------------------------------------------ Deployment Agent
     * ------------------------------------------------
     */

    DeploymentAgent deploymentAgent =
        AgenticServices.agentBuilder(DeploymentAgent.class)
            .chatModel(baseModel)
            .tools(new DeploymentTool())
            .build();

    /*
     * ------------------------------------------------ Dependency Agent
     * ------------------------------------------------
     */

    DependencyAgent dependencyAgent =
        AgenticServices.agentBuilder(DependencyAgent.class)
            .chatModel(baseModel)
            .tools(new DependencyTool())
            .build();

    /*
     * ------------------------------------------------ Remediation Agent
     * ------------------------------------------------
     */

    RemediationAgent remediationAgent =
        AgenticServices.agentBuilder(RemediationAgent.class)
            .chatModel(baseModel)
            .tools(new RunbookTool())
            .build();

    /*
     * ------------------------------------------------ Supervisor
     * ------------------------------------------------
     *
     * Notice:
     *
     * We DON'T define:
     *
     * DeploymentAgent ↓ MetricsAgent ↓ LogAgent ↓ DependencyAgent
     *
     * The supervisor decides the sequence.
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
            .responseStrategy(SupervisorResponseStrategy.SUMMARY)
            .build();

    /*
     * ------------------------------------------------ User Request
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
     * ------------------------------------------------ Invoke Supervisor
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
  }
}
