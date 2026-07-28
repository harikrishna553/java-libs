package com.sample.app.tools;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import java.util.Map;

/**
 * Contract for every executable tool in the Travel Agent.
 *
 * <p>Each implementation represents one discrete capability (e.g., flight search, hotel booking).
 * Tools are registered in the {@link ToolRegistry} and invoked by the {@link
 * com.travelagent.action.ActionModule}.
 *
 * <p>Tools are the only components permitted to produce side effects (user I/O, mock bookings).
 * They may read from and write to {@link AgentMemory} as needed.
 */
public interface Tool {

  /**
   * Returns the unique tool name used to identify and invoke this tool.
   *
   * <p>This name MUST match exactly the {@code toolName} value the LLM places in its JSON plan
   * response. Mismatches cause an "unknown tool" error at runtime.
   *
   * @return unique tool identifier (e.g., {@code "searchFlights"})
   */
  String getName();

  /**
   * Returns a concise English description of what this tool does.
   *
   * <p>Descriptions are injected into the planning prompt so the LLM can choose the correct tool
   * for each step.
   *
   * @return tool description (one sentence)
   */
  String getDescription();

  /**
   * Returns the parameter schema as a human-readable string for the planning prompt.
   *
   * <p>Example: {@code "destination (string), departureDate (string)"}
   *
   * @return parameter descriptions
   */
  String getParameterDescription();

  /**
   * Executes the tool with the given parameters and current agent memory.
   *
   * @param params parameters extracted from the LLM's plan step
   * @param memory current agent state; may be read or mutated
   * @return the result of execution, including an observation string
   */
  ActionResult execute(Map<String, String> params, AgentMemory memory);
}
