package com.sample.app.action;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.PlanStep;
import com.sample.app.tools.Tool;
import com.sample.app.tools.ToolRegistry;
import java.util.Optional;

/**
 * The Action Module — responsible solely for executing a single plan step.
 *
 * <p>
 * The Action Module reads a {@link PlanStep} produced by the Planning Module,
 * looks up the corresponding {@link Tool} in the {@link ToolRegistry}, and
 * executes it. It returns an {@link ActionResult} containing the outcome and an
 * observation string that the Orchestrator will write into {@link AgentMemory}.
 *
 * <p>
 * <strong>Responsibilities:</strong>
 *
 * <ul>
 * <li>Resolve the tool name from the PlanStep to a concrete Tool instance
 * <li>Delegate execution to the Tool
 * <li>Return the ActionResult to the Orchestrator
 * </ul>
 *
 * <p>
 * <strong>Not responsible for:</strong>
 *
 * <ul>
 * <li>Planning — that is the Planner's job
 * <li>Storing observations — that is the Orchestrator + AgentMemory's job
 * <li>Knowing what to execute next — that is the Planner's job
 * </ul>
 */
public class ActionModule {

	private final ToolRegistry toolRegistry;

	/**
	 * Constructs an ActionModule with the given tool registry.
	 *
	 * @param toolRegistry the registry of all available tools
	 */
	public ActionModule(ToolRegistry toolRegistry) {
		this.toolRegistry = toolRegistry;
	}

	/**
	 * Executes the tool specified by the given plan step.
	 *
	 * <p>
	 * If the tool name does not match any registered tool, a failure result is
	 * returned immediately without throwing an exception. This allows the
	 * Orchestrator to record the error as an observation and let the Planner
	 * recover on the next cycle.
	 *
	 * @param step   the plan step specifying which tool to run and with what
	 *               parameters
	 * @param memory the current agent state; passed to the tool for reading and
	 *               writing
	 * @return the result of tool execution, including an observation string
	 */
	public ActionResult execute(PlanStep step, AgentMemory memory) {
		Optional<Tool> toolOpt = toolRegistry.find(step.toolName());

		if (toolOpt.isEmpty()) {
			return ActionResult.failure("Unknown tool: '" + step.toolName() + "'. "
					+ "The LLM requested a tool that is not registered. " + "Registered tools: "
					+ toolRegistry.all().stream().map(Tool::getName).reduce((a, b) -> a + ", " + b).orElse("(none)"));
		}

		Tool tool = toolOpt.get();
		return tool.execute(step.parameters(), memory);
	}
}
