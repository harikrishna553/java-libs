package com.sample.app.util;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.PlanResponse;
import com.sample.app.model.PlanStep;

/**
 * Renders formatted ReAct cycle output to the console.
 *
 * <p>Every planning cycle is printed in a structured block that makes the agent's reasoning process
 * explicit and easy to follow. This is the component that makes this demo "educational" — the
 * reader can watch the agent think, act, and observe in real time.
 *
 * <p>This class contains only presentation logic. It never modifies state, calls tools, or
 * interacts with the LLM.
 */
public class ConsoleRenderer {

  private static final String DIVIDER = "=".repeat(60);
  private static final String THIN = "-".repeat(60);

  /** Prints the application banner on startup. */
  public void printBanner() {
    System.out.println();
    System.out.println(DIVIDER);
    System.out.println("   ✈  TRAVEL PLANNER AI AGENT  ✈");
    System.out.println("   ReAct Architecture Demo");
    System.out.println(DIVIDER);
    System.out.println();
    System.out.println("  Architecture: Perception → Plan → Act → Observe → Re-plan");
    System.out.println("  LLM         : Ollama (llama3.2)");
    System.out.println("  Framework   : LangChain4j (raw ChatModel — no AiServices)");
    System.out.println("  Services    : All mocked — no real APIs");
    System.out.println();
    System.out.println(DIVIDER);
    System.out.println();
  }

  /**
   * Prints the header for a new ReAct cycle.
   *
   * @param cycle the cycle number (1-based)
   * @param memory the current agent memory for state display
   */
  public void printCycleHeader(int cycle, AgentMemory memory) {
    System.out.println();
    System.out.println(DIVIDER);
    System.out.printf("  Planning Cycle %-3d%n", cycle);
    System.out.println(DIVIDER);
    System.out.println();
    System.out.println("  GOAL:");
    System.out.println("    " + memory.getGoal());
    System.out.println();
    System.out.println("  CURRENT STATE:");
    for (String line : memory.getStateSummary().split("\n")) {
      System.out.println("    " + line);
    }
  }

  /**
   * Prints the plan step chosen by the LLM.
   *
   * @param plan the plan response returned by the Planner
   */
  public void printPlan(PlanResponse plan) {
    System.out.println();
    System.out.println("  " + THIN);
    System.out.println("  GENERATED PLAN:");

    if (plan.isCompleted()) {
      System.out.println("    ✅ Status  : COMPLETED");
      System.out.println("    All tasks are done. Goal achieved!");
      return;
    }

    if (plan.isFailed() || plan.nextStep() == null) {
      System.out.println("    ❌ Status  : " + plan.status());
      return;
    }

    PlanStep step = plan.nextStep();
    System.out.println("    Status    : " + plan.status());
    System.out.println("    Tool      : " + step.toolName());
    System.out.println("    Reasoning : " + step.reasoning());
    if (!step.parameters().isEmpty()) {
      System.out.println("    Parameters:");
      step.parameters().forEach((k, v) -> System.out.println("      " + k + " = " + v));
    }
  }

  /**
   * Prints the execution phase label before a tool runs.
   *
   * @param toolName the name of the tool being executed
   */
  public void printExecuting(String toolName) {
    System.out.println();
    System.out.println("  " + THIN);
    System.out.println("  EXECUTING: " + toolName + "()");
    System.out.println("  " + THIN);
  }

  /**
   * Prints the observation returned by a tool after execution.
   *
   * @param result the action result from the tool
   */
  public void printObservation(ActionResult result) {
    System.out.println();
    System.out.println("  OBSERVATION:");
    String icon = result.success() ? "  ✅" : "  ❌";
    for (String line : result.observation().split("\n")) {
      System.out.println(icon + " " + line);
      icon = "    "; // indent continuation lines
    }
  }

  /** Prints the completion message when the goal is fully achieved. */
  public void printCompletion() {
    System.out.println();
    System.out.println(DIVIDER);
    System.out.println("  🎉 GOAL COMPLETE — Your trip has been fully booked!");
    System.out.println(DIVIDER);
    System.out.println();
  }

  /**
   * Prints a warning when the maximum cycle limit is reached.
   *
   * @param maxCycles the cycle limit that was hit
   */
  public void printMaxCyclesReached(int maxCycles) {
    System.out.println();
    System.out.println(DIVIDER);
    System.out.println("  ⚠ MAX CYCLES REACHED (" + maxCycles + ")");
    System.out.println("  The agent did not complete the goal within the cycle limit.");
    System.out.println("  This may indicate a prompt engineering or LLM issue.");
    System.out.println(DIVIDER);
    System.out.println();
  }

  /**
   * Prints an error message.
   *
   * @param message the error description
   */
  public void printError(String message) {
    System.out.println();
    System.out.println("  ❌ ERROR: " + message);
  }

  /** Prints the LLM thinking indicator. */
  public void printThinking() {
    System.out.println();
    System.out.println("  🤔 Asking LLM to plan next step...");
  }
}
