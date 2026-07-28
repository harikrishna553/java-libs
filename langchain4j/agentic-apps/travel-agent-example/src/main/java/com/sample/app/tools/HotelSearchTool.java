package com.sample.app.tools;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.Hotel;
import java.util.List;
import java.util.Map;

/**
 * Mock tool that returns a hardcoded list of available hotels.
 *
 * <p>In a production agent this would call a real hotel search API (Booking.com, Hotels.com, etc.).
 * For this educational demo all hotels are hardcoded. The tool writes results into {@link
 * AgentMemory} so the Planner can reference them on subsequent cycles.
 */
public class HotelSearchTool implements Tool {

  private static final List<Hotel> MOCK_HOTELS =
      List.of(
          new Hotel("H-001", "The Savoy", "London, Strand", 5, 450.00),
          new Hotel("H-002", "Premier Inn Waterloo", "London, Waterloo", 3, 120.00),
          new Hotel("H-003", "The Hoxton Shoreditch", "London, Shoreditch", 4, 210.00));

  @Override
  public String getName() {
    return "searchHotels";
  }

  @Override
  public String getDescription() {
    return "Searches for available hotels at the destination and returns a list of options.";
  }

  @Override
  public String getParameterDescription() {
    return "destination (string) - the city to search hotels in";
  }

  /**
   * Returns 3 mock hotels and stores them in memory.
   *
   * @param params should contain key {@code destination}
   * @param memory agent memory; available hotels list will be updated
   * @return successful ActionResult with observation describing the results
   */
  @Override
  public ActionResult execute(Map<String, String> params, AgentMemory memory) {
    String destination = params.getOrDefault("destination", "unknown");

    memory.setAvailableHotels(MOCK_HOTELS);

    StringBuilder obs = new StringBuilder();
    obs.append("Found ")
        .append(MOCK_HOTELS.size())
        .append(" hotels in ")
        .append(destination)
        .append(":\n");
    for (Hotel h : MOCK_HOTELS) {
      obs.append("  ").append(h.display()).append("\n");
    }

    return ActionResult.success(obs.toString().trim(), MOCK_HOTELS);
  }
}
