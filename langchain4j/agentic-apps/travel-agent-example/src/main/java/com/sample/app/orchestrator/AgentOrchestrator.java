package com.sample.app.orchestrator;

import com.sample.app.action.ActionModule;
import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.Goal;
import com.sample.app.model.PlanResponse;
import com.sample.app.model.PlanStep;
import com.sample.app.planning.Planner;
import com.sample.app.util.ConsoleRenderer;

/**
 * Coordinates the complete ReAct (Reason + Act) loop for the Travel Agent.
 *
 * <p>The Orchestrator is the top-level controller that repeatedly executes:
 *
 * <pre>
 *   Perceive Goal
 *       ↓
 *   ┌─────────────────────────────┐
 *   │  1. THINK  → call Planner   │
 *   │  2. ACT    → call Action    │  ← repeats until COMPLETED or max cycles
 *   │  3. OBSERVE → update Memory │
 *   └─────────────────────────────┘
 *       ↓
 *   Goal Complete / Max Cycles
 * </pre>
 *
 * <p>The Orchestrator knows about every module but delegates all decisions:
 *
 * <ul>
 *   <li><strong>What to do next</strong> → {@link Planner}
 *   <li><strong>How to do it</strong> → {@link ActionModule} + Tools
 *   <li><strong>What happened</strong> → {@link AgentMemory}
 *   <li><strong>How to display it</strong> → {@link ConsoleRenderer}
 * </ul>
 *
 * <p>A maximum cycle cap prevents infinite loops if the LLM fails to signal completion.
 */
public class AgentOrchestrator {

  /** Safety cap: prevents the agent from looping forever if the LLM misbehaves. */
  private static final int MAX_CYCLES = 100;

  private final Planner planner;
  private final ActionModule actionModule;
  private final AgentMemory memory;
  private final ConsoleRenderer renderer;

  /**
   * Constructs an AgentOrchestrator with all required collaborators.
   *
   * @param planner the planning module for generating next steps
   * @param actionModule the action module for executing plan steps
   * @param memory the shared agent state
   * @param renderer the console renderer for formatted output
   */
  public AgentOrchestrator(
      Planner planner, ActionModule actionModule, AgentMemory memory, ConsoleRenderer renderer) {
    this.planner = planner;
    this.actionModule = actionModule;
    this.memory = memory;
    this.renderer = renderer;
  }

  /**
   * Runs the full ReAct loop for the given goal until it is completed or the cycle cap is hit.
   *
   * <p>Each iteration of the loop represents one full Think-Act-Observe cycle:
   *
   * <ol>
   *   <li><strong>Think</strong>: The Planner queries the LLM with the current memory state and
   *       receives a single next step as structured JSON.
   *   <li><strong>Act</strong>: The Action Module looks up and executes the tool named in the plan
   *       step.
   *   <li><strong>Observe</strong>: The tool's result (observation) is written into memory so the
   *       Planner has full context on the next cycle.
   * </ol>
   *
   * @param goal the travel goal produced by the Perception Module
   */
  public void run(Goal goal) {
    memory.setGoal(goal);

    for (int cycle = 1; cycle <= MAX_CYCLES; cycle++) {

      // ── Display cycle header ─────────────────────────────────────────
      renderer.printCycleHeader(cycle, memory);

      // ── THINK: ask the LLM for the next step ─────────────────────────
      renderer.printThinking();
      PlanResponse plan = planner.plan(memory);
      renderer.printPlan(plan);

      // ── Check for terminal states ─────────────────────────────────────
      if (plan.isCompleted()) {
        renderer.printCompletion();
        return;
      }

      if (plan.isFailed()) {
        renderer.printError("Planner signalled FAILED: " + plan.status());
        return;
      }

      if (plan.nextStep() == null) {
        renderer.printError("Planner returned null nextStep without COMPLETED status. Aborting.");
        return;
      }

      // ── Handle parse errors returned as a special tool name ────────────
      PlanStep step = plan.nextStep();
      if ("_parse_error_".equals(step.toolName())) {
        renderer.printError("LLM response could not be parsed as JSON.");
        renderer.printError(
            "Raw response: " + step.parameters().getOrDefault("rawResponse", "(empty)"));
        renderer.printError("Parse error : " + step.reasoning());
        // Record in memory so LLM has context on the next cycle
        memory.addObservation(
            "Parse error on previous cycle: LLM did not return valid JSON. Please return only JSON.");
        continue;
      }

      // ── ACT: execute the tool ─────────────────────────────────────────
      renderer.printExecuting(step.toolName());
      ActionResult result = actionModule.execute(step, memory);

      // ── OBSERVE: record result in memory ──────────────────────────────
      renderer.printObservation(result);
      memory.addObservation(
          "Cycle " + cycle + " | Tool: " + step.toolName() + " | " + result.observation());

      // If the tool failed, give the LLM a chance to recover on the next cycle
      if (!result.success()) {
        memory.addObservation(
            "The previous tool failed. Consider whether the same tool should be retried "
                + "or a prerequisite step is missing.");
      }

      // ── Deterministic exit: do not rely solely on LLM signalling COMPLETED ──
      // If the booking workflow is fully done, exit immediately.
      if (memory.isBookingWorkflowComplete()) {
        renderer.printCompletion();
        return;
      }

      // If tour plans were suggested and that was the user's goal, exit.
      if (memory.isTourPlansSuggested() && !memory.isFlightBooked()) {
        renderer.printCompletion();
        return;
      }
    }

    // Loop exhausted without COMPLETED signal
    renderer.printMaxCyclesReached(MAX_CYCLES);
  }
}
