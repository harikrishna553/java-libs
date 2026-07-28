package com.sample.app.perception;

import com.sample.app.model.Goal;

/**
 * Converts raw user input into a structured {@link Goal}.
 *
 * <p>The Perception Module is the agent's entry point for understanding what the user wants. It
 * sits between the raw console input and the Planning Module.
 *
 * <p>In a more sophisticated agent, this module would use an LLM to extract intents, entities
 * (destination, dates, budget), and constraints from free-form text. For this educational demo it
 * performs lightweight normalization only, keeping the module boundary clear without
 * over-engineering.
 *
 * <p><strong>Responsibilities:</strong>
 *
 * <ul>
 *   <li>Validate that input is non-empty
 *   <li>Normalize whitespace and casing
 *   <li>Wrap the result in a {@link Goal} record
 * </ul>
 *
 * <p><strong>Not responsible for:</strong>
 *
 * <ul>
 *   <li>Planning — that is the Planning Module's job
 *   <li>Calling tools — that is the Action Module's job
 *   <li>Storing state — that is AgentMemory's job
 * </ul>
 */
public class PerceptionModule {

  /**
   * Parses the raw user input string into a normalized {@link Goal}.
   *
   * @param rawInput the unprocessed string typed by the user at the console
   * @return a Goal representing the user's normalized travel intent
   * @throws IllegalArgumentException if the input is null or blank
   */
  public Goal perceive(String rawInput) {
    if (rawInput == null || rawInput.isBlank()) {
      throw new IllegalArgumentException(
          "User input must not be empty. Please describe your travel goal.");
    }

    String normalized = rawInput.trim();

    // Ensure the goal ends with a period for clean prompt injection
    if (!normalized.endsWith(".") && !normalized.endsWith("!") && !normalized.endsWith("?")) {
      normalized = normalized + ".";
    }

    return new Goal(normalized);
  }
}
