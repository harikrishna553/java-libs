package com.sample.app.tools;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Registry that holds all available tools, keyed by their unique name.
 *
 * <p>The ToolRegistry is the single source of truth for what tools exist. It is consulted by the
 * {@link com.travelagent.action.ActionModule} to dispatch plan steps, and by the {@link
 * com.travelagent.llm.PromptBuilder} to generate the tools section of the planning prompt.
 *
 * <p>Tool names in the registry MUST exactly match the names the LLM places in its JSON responses.
 * Any drift between registry key and LLM output causes a dispatch failure at runtime.
 */
public class ToolRegistry {

  private final Map<String, Tool> tools;

  /**
   * Constructs a ToolRegistry pre-populated with all travel agent tools.
   *
   * @param scanner the Scanner used by {@link AskUserTool} for reading user input
   */
  public ToolRegistry(java.util.Scanner scanner) {
    Map<String, Tool> map = new LinkedHashMap<>();
    register(map, new SuggestTourPlansTool());
    register(map, new FlightSearchTool());
    register(map, new FlightBookTool());
    register(map, new HotelSearchTool());
    register(map, new HotelBookTool());
    register(map, new AskUserTool(scanner));
    register(map, new EmailTool());
    this.tools = Collections.unmodifiableMap(map);
  }

  /**
   * Looks up a tool by its registered name.
   *
   * @param name the tool name (must be an exact, case-sensitive match)
   * @return an Optional containing the tool, or empty if not found
   */
  public Optional<Tool> find(String name) {
    return Optional.ofNullable(tools.get(name));
  }

  /**
   * Returns all registered tools, in registration order.
   *
   * @return unmodifiable collection of tools
   */
  public Collection<Tool> all() {
    return tools.values();
  }

  private void register(Map<String, Tool> map, Tool tool) {
    map.put(tool.getName(), tool);
  }
}
