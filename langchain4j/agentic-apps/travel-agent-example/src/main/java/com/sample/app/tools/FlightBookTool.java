package com.sample.app.tools;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.Flight;
import java.util.Map;

/**
 * Mock tool that books the user-selected flight.
 *
 * <p>Requires that a flight has already been selected and stored in {@link AgentMemory}. In
 * production this would call an airline booking API. Here it simply marks the flight as booked in
 * memory and returns a confirmation observation.
 */
public class FlightBookTool implements Tool {

  @Override
  public String getName() {
    return "bookFlight";
  }

  @Override
  public String getDescription() {
    return "Books the flight that was previously selected by the user. No parameters needed.";
  }

  @Override
  public String getParameterDescription() {
    return "(no parameters required - books the currently selected flight from memory)";
  }

  /**
   * Books the currently selected flight from memory.
   *
   * @param params not required for this tool
   * @param memory must contain a selected flight ({@link AgentMemory#getSelectedFlight()})
   * @return successful ActionResult with booking confirmation, or failure if no flight selected
   */
  @Override
  public ActionResult execute(Map<String, String> params, AgentMemory memory) {
    Flight flight = memory.getSelectedFlight();

    if (flight == null) {
      return ActionResult.failure(
          "Cannot book flight: no flight has been selected yet. Ask the user to choose one first.");
    }

    memory.setFlightBooked(true);

    String observation =
        String.format(
            "Flight booked successfully! Booking reference: CONF-%s. %s",
            flight.id(), flight.display());

    return ActionResult.success(observation);
  }
}
