package com.sample.app;

import com.sample.app.action.ActionModule;
import com.sample.app.llm.LlmClient;
import com.sample.app.llm.PlanParser;
import com.sample.app.llm.PromptBuilder;
import com.sample.app.memory.AgentMemory;
import com.sample.app.model.Goal;
import com.sample.app.orchestrator.AgentOrchestrator;
import com.sample.app.perception.PerceptionModule;
import com.sample.app.planning.Planner;
import com.sample.app.tools.ToolRegistry;
import com.sample.app.util.ConsoleRenderer;
import java.util.Scanner;

/**
 * Entry point for the Travel Planner AI Agent demo.
 *
 * <p>This class is responsible for wiring all modules together and starting the agent. There is no
 * dependency injection framework — all collaborators are constructed and connected here by hand,
 * making the dependency graph explicit and readable.
 *
 * <p>The architecture demonstrated here is the ReAct (Reason + Act) loop:
 *
 * <pre>
 *
 *   User Input (console)
 *        │
 *        ▼
 *   PerceptionModule  →  Goal
 *        │
 *        ▼
 *   AgentOrchestrator ─────────────────────────────────────────┐
 *        │                                                     │
 *        ▼                                                     │ loop
 *   Planner ─→ LlmClient ─→ Ollama LLM                        │
 *        │         ↑                                           │
 *        │    PromptBuilder (reads AgentMemory)                │
 *        │         ↓                                           │
 *        │    PlanParser (parses JSON response)                │
 *        │                                                     │
 *        ▼                                                     │
 *   ActionModule ─→ ToolRegistry ─→ Tool.execute()            │
 *        │                                                     │
 *        ▼                                                     │
 *   AgentMemory.addObservation()  ─────────────────────────────┘
 * </pre>
 *
 * <p><strong>Prerequisites:</strong>
 *
 * <ul>
 *   <li>Ollama running locally on {@code http://localhost:11434}
 *   <li>Model {@code llama3.2} pulled: {@code ollama pull llama3.2}
 * </ul>
 *
 * <p><strong>Run:</strong>
 *
 * <pre>
 *   mvn package
 *   java -jar target/travel-agent-demo-1.0.0-SNAPSHOT.jar
 * </pre>
 */
public class App {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    ConsoleRenderer renderer = new ConsoleRenderer();

    renderer.printBanner();

    // ── 1. Read the user's travel goal ───────────────────────────────────
    System.out.print("  Enter your travel goal: ");
    String rawInput = scanner.nextLine();
    System.out.println();

    // ── 2. Wire all modules (no framework, no DI container) ──────────────

    // LLM layer
    LlmClient llmClient = new LlmClient();
    PromptBuilder promptBuilder = new PromptBuilder();
    PlanParser planParser = new PlanParser();

    // Tool layer
    ToolRegistry toolRegistry = new ToolRegistry(scanner);

    // Core modules
    AgentMemory memory = new AgentMemory();
    PerceptionModule perception = new PerceptionModule();
    Planner planner = new Planner(llmClient, promptBuilder, planParser, toolRegistry);
    ActionModule actionModule = new ActionModule(toolRegistry);

    // Orchestrator (coordinates the ReAct loop)
    AgentOrchestrator orchestrator = new AgentOrchestrator(planner, actionModule, memory, renderer);

    // ── 3. Perceive the user's goal ──────────────────────────────────────
    Goal goal;
    try {
      goal = perception.perceive(rawInput);
    } catch (IllegalArgumentException e) {
      renderer.printError(e.getMessage());
      scanner.close();
      return;
    }

    System.out.println("  ✅ Goal understood: " + goal);
    System.out.println();

    // ── 4. Run the ReAct loop ─────────────────────────────────────────────
    orchestrator.run(goal);

    scanner.close();
  }
}
