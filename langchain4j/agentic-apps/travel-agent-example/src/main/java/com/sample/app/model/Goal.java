package com.sample.app.model;

/**
 * Represents the user's travel goal as understood by the Perception Module.
 *
 * <p>A Goal is immutable and carries the original user request after it has been parsed and
 * normalized by the Perception Module. It is passed into the Planning Module at the start of every
 * ReAct cycle.
 *
 * @param description A concise, normalized description of what the user wants to achieve.
 */
public record Goal(String description) {

  /**
   * Constructs a Goal, validating that the description is not blank.
   *
   * @param description the normalized goal description
   * @throws IllegalArgumentException if description is null or blank
   */
  public Goal {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Goal description must not be blank");
    }
  }

  @Override
  public String toString() {
    return description;
  }
}
