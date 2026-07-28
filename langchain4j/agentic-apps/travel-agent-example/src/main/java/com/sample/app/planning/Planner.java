package com.sample.app.planning;

import com.sample.app.llm.LlmClient;
import com.sample.app.llm.PlanParser;
import com.sample.app.llm.PromptBuilder;
import com.sample.app.memory.AgentMemory;
import com.sample.app.model.PlanResponse;
import com.sample.app.tools.ToolRegistry;

/**
 * The Planning Module — responsible solely for reasoning about what to do next.
 *
 * <p>On every ReAct cycle, the Planner:
 *
 * <ol>
 *   <li>Reads the current state from {@link AgentMemory}
 *   <li>Builds a context-rich prompt via {@link PromptBuilder}
 *   <li>Sends the prompt to the LLM via {@link LlmClient}
 *   <li>Parses the raw JSON response via {@link PlanParser}
 *   <li>Returns a single {@link PlanResponse} containing ONE next step
 * </ol>
 *
 * <p><strong>Critical constraints — the Planner NEVER:</strong>
 *
 * <ul>
 *   <li>Executes tools
 *   <li>Calls external APIs
 *   <li>Modifies memory
 *   <li>Generates a complete workflow
 * </ul>
 *
 * <p>It only produces a structured plan for the Action Module to execute. This separation is what
 * makes the ReAct loop composable and testable.
 */
public class Planner {

  private final LlmClient llmClient;
  private final PromptBuilder promptBuilder;
  private final PlanParser planParser;
  private final ToolRegistry toolRegistry;

  /**
   * Constructs a Planner with all required collaborators.
   *
   * @param llmClient the client for communicating with the LLM
   * @param promptBuilder builds the planning prompt from memory state
   * @param planParser parses the LLM JSON response into a PlanResponse
   * @param toolRegistry provides tool metadata for the prompt's tools section
   */
  public Planner(
      LlmClient llmClient,
      PromptBuilder promptBuilder,
      PlanParser planParser,
      ToolRegistry toolRegistry) {
    this.llmClient = llmClient;
    this.promptBuilder = promptBuilder;
    this.planParser = planParser;
    this.toolRegistry = toolRegistry;
  }

  /**
   * Generates the next plan step by querying the LLM with the current agent state.
   *
   * <p>This method represents a single "Think" phase in the ReAct loop. It returns exactly one step
   * to execute — never a full workflow. The Orchestrator calls this method on every cycle after
   * observing the previous step's result.
   *
   * @param memory current agent state including goal, observations, and booking flags
   * @return a PlanResponse with either the next step or a COMPLETED/FAILED signal
   */
  public PlanResponse plan(AgentMemory memory) {
    String prompt = promptBuilder.build(memory, toolRegistry);
    String rawResponse = llmClient.generate(prompt);
    return planParser.parse(rawResponse);
  }
}
