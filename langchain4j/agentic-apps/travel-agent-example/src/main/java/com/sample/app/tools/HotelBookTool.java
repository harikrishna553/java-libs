package com.sample.app.tools;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.Hotel;
import java.util.Map;

/**
 * Mock tool that books the user-selected hotel.
 *
 * <p>Requires that a hotel has already been selected and stored in {@link AgentMemory}. In
 * production this would call a hotel booking API. Here it simply marks the hotel as booked in
 * memory and returns a confirmation observation.
 */
public class HotelBookTool implements Tool {

  @Override
  public String getName() {
    return "bookHotel";
  }

  @Override
  public String getDescription() {
    return "Books the hotel that was previously selected by the user. No parameters needed.";
  }

  @Override
  public String getParameterDescription() {
    return "(no parameters required - books the currently selected hotel from memory)";
  }

  /**
   * Books the currently selected hotel from memory.
   *
   * @param params not required for this tool
   * @param memory must contain a selected hotel ({@link AgentMemory#getSelectedHotel()})
   * @return successful ActionResult with booking confirmation, or failure if no hotel selected
   */
  @Override
  public ActionResult execute(Map<String, String> params, AgentMemory memory) {
    Hotel hotel = memory.getSelectedHotel();

    if (hotel == null) {
      return ActionResult.failure(
          "Cannot book hotel: no hotel has been selected yet. Ask the user to choose one first.");
    }

    memory.setHotelBooked(true);

    String observation =
        String.format(
            "Hotel booked successfully! Booking reference: HTL-%s. %s",
            hotel.id(), hotel.display());

    return ActionResult.success(observation);
  }
}
