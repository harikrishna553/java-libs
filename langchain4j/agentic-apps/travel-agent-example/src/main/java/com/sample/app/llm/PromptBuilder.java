package com.sample.app.llm;

import com.sample.app.memory.AgentMemory;
import com.sample.app.tools.Tool;
import com.sample.app.tools.ToolRegistry;
import java.util.Collection;
import java.util.List;

/**
 * Builds the planning prompt that is sent to the LLM on every ReAct cycle.
 *
 * <p>The prompt is the mechanism by which the agent's current knowledge is communicated to the LLM.
 * It contains four sections:
 *
 * <ol>
 *   <li><strong>System Instructions</strong> — role, output format contract, constraints
 *   <li><strong>Available Tools</strong> — name, description, and parameters of every tool
 *   <li><strong>Current State</strong> — what has been booked, what has been observed
 *   <li><strong>Task</strong> — the goal and the question: "what is the ONE next step?"
 * </ol>
 *
 * <p>This class contains no LLM interaction and no business logic — only prompt text assembly.
 */
public class PromptBuilder {

  private static final String SYSTEM_INSTRUCTIONS =
      """
            You are a travel planning AI agent executing a ReAct (Reason + Act) loop.

            Your job is to read the user's GOAL, look at the CURRENT STATE, and decide the SINGLE NEXT ACTION.

            STRICT OUTPUT RULES:
            - Respond with valid JSON ONLY. No prose, no markdown fences, no explanation outside the JSON.
            - Choose exactly ONE tool whose name exactly matches a name in the AVAILABLE TOOLS list.
            - Use status "IN_PROGRESS" while more steps are needed.
            - Use status "COMPLETED" with nextStep null when the goal is fully satisfied.

            HOW TO REASON ABOUT THE GOAL:
            - If the goal is about SUGGESTING or EXPLORING trip ideas (e.g. "show me tours", "suggest plans", "give me ideas"):
                → Use suggestTourPlans. After it runs, set status=COMPLETED.
            - If the goal is about BOOKING a trip (e.g. "book my trip", "I want to fly to X"):
                → Follow the booking workflow below, in order.
            - If the goal is ambiguous, prefer suggestTourPlans first and ask the user.

            BOOKING WORKFLOW (only when goal is to book):
            Step 1  — searchFlights       : find available flights
            Step 2  — askUser             : questionType=flight (let user pick a flight)
            Step 3  — bookFlight          : confirm the chosen flight
            Step 4  — searchHotels        : find available hotels
            Step 5  — askUser             : questionType=hotel (let user pick a hotel)
            Step 6  — bookHotel           : confirm the chosen hotel
            Step 7  — sendEmail           : send confirmation
            Step 8  — status=COMPLETED

            RECOVERY RULES:
            - If a tool returned a failure observation, analyse why and choose the corrective next step.
            - If askUser failed because of an invalid ID, call askUser again with the same questionType.
            - Never repeat a step that already succeeded (check CURRENT STATE before deciding).

            OUTPUT FORMAT:
            {
              "status": "IN_PROGRESS",
              "nextStep": {
                "toolName": "<exact tool name>",
                "parameters": { "<key>": "<value>" },
                "reasoning": "<one sentence explaining why this step is next>"
              }
            }

            When goal is fully satisfied:
            {
              "status": "COMPLETED",
              "nextStep": null
            }
            """;

  /**
   * Builds the full planning prompt for the current ReAct cycle.
   *
   * @param memory current agent state (goal, observations, booking flags)
   * @param registry the tool registry used to enumerate available tools
   * @return the complete prompt string ready to send to the LLM
   */
  public String build(AgentMemory memory, ToolRegistry registry) {
    StringBuilder prompt = new StringBuilder();

    prompt.append("=== SYSTEM INSTRUCTIONS ===\n");
    prompt.append(SYSTEM_INSTRUCTIONS);
    prompt.append("\n");

    prompt.append("=== AVAILABLE TOOLS ===\n");
    prompt.append(buildToolsSection(registry.all()));
    prompt.append("\n");

    prompt.append("=== CURRENT STATE ===\n");
    prompt.append("Goal: ").append(memory.getGoal()).append("\n\n");
    prompt.append(memory.getStateSummary()).append("\n");

    prompt.append("=== OBSERVATIONS (most recent last) ===\n");
    List<String> observations = memory.getObservations();
    if (observations.isEmpty()) {
      prompt.append("No observations yet.\n");
    } else {
      for (int i = 0; i < observations.size(); i++) {
        prompt.append("[").append(i + 1).append("] ").append(observations.get(i)).append("\n");
      }
    }
    prompt.append("\n");

    prompt.append("=== TASK ===\n");
    prompt.append(
        "Based on the current state and observations above, what is the SINGLE NEXT STEP ");
    prompt.append("to complete the travel goal?\n\n");
    prompt.append("Respond with JSON only. No other text.\n");

    return prompt.toString();
  }

  /**
   * Builds the tools section of the prompt, listing each tool with its description and parameters
   * so the LLM can choose the appropriate one.
   *
   * @param tools all registered tools
   * @return formatted tools section string
   */
  private String buildToolsSection(Collection<Tool> tools) {
    StringBuilder sb = new StringBuilder();
    for (Tool tool : tools) {
      sb.append("Tool: ").append(tool.getName()).append("\n");
      sb.append("  Description : ").append(tool.getDescription()).append("\n");
      sb.append("  Parameters  : ").append(tool.getParameterDescription()).append("\n");
      sb.append("\n");
    }
    return sb.toString();
  }
}
