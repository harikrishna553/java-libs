package com.sample.app.model;

/**
 * The structured response produced by the Planning Module after each LLM call.
 *
 * <p>This is the Java representation of the JSON that the LLM must return on every planning cycle.
 * The expected JSON shape is:
 *
 * <pre>{@code
 * {
 *   "status": "IN_PROGRESS",
 *   "nextStep": {
 *     "toolName": "searchFlights",
 *     "parameters": { "destination": "London" },
 *     "reasoning": "Need to find available flights first."
 *   }
 * }
 * }</pre>
 *
 * <p>When the goal is complete the LLM returns {@code "status": "COMPLETED"} and {@code "nextStep":
 * null}.
 *
 * @param status The planning status. One of: {@code IN_PROGRESS}, {@code COMPLETED}, {@code
 *     FAILED}.
 * @param nextStep The single next action to execute. {@code null} when status is COMPLETED or
 *     FAILED.
 */
public record PlanResponse(String status, PlanStep nextStep) {

  /** Convenience constant for the completed status value. */
  public static final String STATUS_COMPLETED = "COMPLETED";

  /** Convenience constant for the in-progress status value. */
  public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";

  /** Convenience constant for the failed status value. */
  public static final String STATUS_FAILED = "FAILED";

  /**
   * Returns {@code true} if the agent has signalled that the goal is fully complete.
   *
   * @return true when status equals COMPLETED
   */
  public boolean isCompleted() {
    return STATUS_COMPLETED.equalsIgnoreCase(status);
  }

  /**
   * Returns {@code true} if the agent has signalled an unrecoverable failure.
   *
   * @return true when status equals FAILED
   */
  public boolean isFailed() {
    return STATUS_FAILED.equalsIgnoreCase(status);
  }
}
