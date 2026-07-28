package com.sample.app.tools;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.Flight;
import com.sample.app.model.Hotel;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Tool that pauses the agent loop and asks the human user a question.
 *
 * <p>This tool is used when the agent needs human input to proceed — for example, to let the user
 * choose a flight or hotel from the discovered options. It reads from standard input and writes the
 * selected item back to {@link AgentMemory}.
 *
 * <p>The {@code questionType} parameter tells this tool what kind of choice to present:
 *
 * <ul>
 *   <li>{@code "flight"} — presents available flights and records the selection
 *   <li>{@code "hotel"} — presents available hotels and records the selection
 * </ul>
 */
public class AskUserTool implements Tool {

  private final Scanner scanner;

  /**
   * Constructs an AskUserTool with the given Scanner for reading user input.
   *
   * @param scanner the Scanner connected to System.in
   */
  public AskUserTool(Scanner scanner) {
    this.scanner = scanner;
  }

  @Override
  public String getName() {
    return "askUser";
  }

  @Override
  public String getDescription() {
    return "Asks the human user to make a selection (choose a flight or hotel) and records their choice.";
  }

  @Override
  public String getParameterDescription() {
    return "questionType (string) - either 'flight' to let user pick a flight, or 'hotel' to let user pick a hotel";
  }

  /**
   * Presents options to the user and records their selection in memory.
   *
   * @param params must contain {@code questionType} = "flight" or "hotel"
   * @param memory agent memory; selected flight or hotel will be written here
   * @return successful ActionResult describing the user's choice
   */
  @Override
  public ActionResult execute(Map<String, String> params, AgentMemory memory) {
    String questionType = params.getOrDefault("questionType", "flight");

    if ("flight".equalsIgnoreCase(questionType)) {
      return askForFlight(memory);
    } else if ("hotel".equalsIgnoreCase(questionType)) {
      return askForHotel(memory);
    } else {
      return ActionResult.failure(
          "Unknown questionType: '" + questionType + "'. Use 'flight' or 'hotel'.");
    }
  }

  /**
   * Displays available flights, reads one line of user input, and returns either a success (valid
   * ID) or failure (invalid ID) so the ReAct loop can let the LLM decide whether to call this tool
   * again.
   *
   * @param memory agent memory with available flights
   * @return success if the user entered a valid flight ID, failure otherwise
   */
  private ActionResult askForFlight(AgentMemory memory) {
    List<Flight> flights = memory.getAvailableFlights();

    if (flights.isEmpty()) {
      return ActionResult.failure("No flights available to choose from. Run searchFlights first.");
    }

    List<String> validIds = flights.stream().map(Flight::id).toList();

    System.out.println();
    System.out.println("  ┌─────────────────────────────────────────┐");
    System.out.println("  │   Please choose your flight:            │");
    System.out.println("  └─────────────────────────────────────────┘");
    for (Flight f : flights) {
      System.out.println("    " + f.display());
    }
    System.out.print("\n  Enter flight ID " + validIds + ": ");

    String input = scanner.nextLine().trim();

    Flight selected =
        flights.stream().filter(f -> f.id().equalsIgnoreCase(input)).findFirst().orElse(null);

    if (selected == null) {
      return ActionResult.failure(
          "User entered '"
              + input
              + "' which is not a valid flight ID. "
              + "Valid options are: "
              + validIds
              + ". Please ask the user to choose again.");
    }

    memory.setSelectedFlight(selected);
    return ActionResult.success("User selected flight: " + selected.display(), selected);
  }

  /**
   * Displays available hotels, reads one line of user input, and returns either a success (valid
   * ID) or failure (invalid ID) so the ReAct loop can let the LLM decide whether to call this tool
   * again.
   *
   * @param memory agent memory with available hotels
   * @return success if the user entered a valid hotel ID, failure otherwise
   */
  private ActionResult askForHotel(AgentMemory memory) {
    List<Hotel> hotels = memory.getAvailableHotels();

    if (hotels.isEmpty()) {
      return ActionResult.failure("No hotels available to choose from. Run searchHotels first.");
    }

    List<String> validIds = hotels.stream().map(Hotel::id).toList();

    System.out.println();
    System.out.println("  ┌─────────────────────────────────────────┐");
    System.out.println("  │   Please choose your hotel:             │");
    System.out.println("  └─────────────────────────────────────────┘");
    for (Hotel h : hotels) {
      System.out.println("    " + h.display());
    }
    System.out.print("\n  Enter hotel ID " + validIds + ": ");

    String input = scanner.nextLine().trim();

    Hotel selected =
        hotels.stream().filter(h -> h.id().equalsIgnoreCase(input)).findFirst().orElse(null);

    if (selected == null) {
      return ActionResult.failure(
          "User entered '"
              + input
              + "' which is not a valid hotel ID. "
              + "Valid options are: "
              + validIds
              + ". Please ask the user to choose again.");
    }

    memory.setSelectedHotel(selected);
    return ActionResult.success("User selected hotel: " + selected.display(), selected);
  }
}
