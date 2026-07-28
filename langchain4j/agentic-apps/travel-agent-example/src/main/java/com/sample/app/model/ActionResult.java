package com.sample.app.model;

/**
 * Represents the outcome of a tool execution performed by the Action Module.
 *
 * <p>After each tool runs, the Action Module wraps the result in an ActionResult and passes it back
 * to the Orchestrator, which writes the observation into AgentMemory. The Planner reads these
 * observations on the next cycle to decide what to do next — this is the Observe step of the ReAct
 * loop.
 *
 * @param success {@code true} if the tool executed without error.
 * @param observation A human-readable summary of what happened (e.g., "3 flights found"). This text
 *     is injected verbatim into the next planning prompt.
 * @param data Optional structured payload (e.g., a {@code List<Flight>}). May be {@code null} when
 *     the tool produces no structured output.
 */
public record ActionResult(boolean success, String observation, Object data) {

  /**
   * Factory method for a successful result with no structured data.
   *
   * @param observation human-readable summary
   * @return successful ActionResult
   */
  public static ActionResult success(String observation) {
    return new ActionResult(true, observation, null);
  }

  /**
   * Factory method for a successful result with structured data.
   *
   * @param observation human-readable summary
   * @param data structured result payload
   * @return successful ActionResult
   */
  public static ActionResult success(String observation, Object data) {
    return new ActionResult(true, observation, data);
  }

  /**
   * Factory method for a failed result.
   *
   * @param observation description of what went wrong
   * @return failed ActionResult
   */
  public static ActionResult failure(String observation) {
    return new ActionResult(false, observation, null);
  }
}
