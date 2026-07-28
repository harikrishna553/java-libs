package com.sample.app.tools;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.Flight;
import java.util.List;
import java.util.Map;

/**
 * Mock tool that returns a hardcoded list of available flights.
 *
 * <p>In a production agent this would call a real flight search API (Skyscanner, Amadeus, etc.).
 * For this educational demo all flights are hardcoded. The tool writes the results into {@link
 * AgentMemory} so the Planner can reference them on subsequent cycles.
 */
public class FlightSearchTool implements Tool {

  private static final List<Flight> MOCK_FLIGHTS =
      List.of(
          new Flight("BA-101", "British Airways", "JFK", "LHR", "2025-07-28", 650.00),
          new Flight("VS-202", "Virgin Atlantic", "JFK", "LHR", "2025-07-28", 580.00),
          new Flight("AA-303", "American Airlines", "JFK", "LHR", "2025-07-29", 510.00));

  @Override
  public String getName() {
    return "searchFlights";
  }

  @Override
  public String getDescription() {
    return "Searches for available flights to the given destination and returns a list of options.";
  }

  @Override
  public String getParameterDescription() {
    return "destination (string) - the travel destination city name";
  }

  /**
   * Returns 3 mock flights and stores them in memory.
   *
   * @param params must contain key {@code destination}
   * @param memory agent memory; available flights list will be updated
   * @return successful ActionResult with observation describing the results
   */
  @Override
  public ActionResult execute(Map<String, String> params, AgentMemory memory) {
    String destination = params.getOrDefault("destination", "unknown");

    memory.setAvailableFlights(MOCK_FLIGHTS);

    StringBuilder obs = new StringBuilder();
    obs.append("Found ")
        .append(MOCK_FLIGHTS.size())
        .append(" flights to ")
        .append(destination)
        .append(":\n");
    for (Flight f : MOCK_FLIGHTS) {
      obs.append("  ").append(f.display()).append("\n");
    }

    return ActionResult.success(obs.toString().trim(), MOCK_FLIGHTS);
  }
}
