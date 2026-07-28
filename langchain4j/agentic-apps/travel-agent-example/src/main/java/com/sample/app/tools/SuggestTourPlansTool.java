package com.sample.app.tools;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tool that suggests curated tour plans for popular travel destinations.
 *
 * <p>Used when the user's goal is to explore travel options or get itinerary ideas rather than
 * immediately booking a trip. Returns a set of hardcoded tour plans and marks them as suggested in
 * {@link AgentMemory} so the Planner knows the goal has been fulfilled.
 *
 * <p>In production this would call a travel recommendations API or query a knowledge base. For this
 * demo all plans are hardcoded.
 */
public class SuggestTourPlansTool implements Tool {

  private static final Map<String, String[]> TOUR_PLANS = new LinkedHashMap<>();

  static {
    TOUR_PLANS.put(
        "London Explorer (5 Days)",
        new String[] {
          "Day 1 : Arrive, check in, evening walk along the Thames",
          "Day 2 : Tower of London + Tower Bridge + Borough Market",
          "Day 3 : British Museum + Covent Garden + West End show",
          "Day 4 : Kensington Palace + Hyde Park + Notting Hill",
          "Day 5 : Greenwich Observatory + Cutty Sark, depart"
        });
    TOUR_PLANS.put(
        "Paris Getaway (4 Days)",
        new String[] {
          "Day 1 : Arrive, Eiffel Tower at sunset, Seine river cruise",
          "Day 2 : Louvre Museum + Tuileries Garden + Champs-Élysées",
          "Day 3 : Montmartre + Sacré-Cœur + local bistro dinner",
          "Day 4 : Palace of Versailles day trip, depart evening"
        });
    TOUR_PLANS.put(
        "Tokyo Adventure (7 Days)",
        new String[] {
          "Day 1 : Arrive, Shinjuku exploration",
          "Day 2 : Senso-ji Temple + Akihabara electronics district",
          "Day 3 : Tsukiji market + teamLab digital art museum",
          "Day 4 : Day trip to Nikko (UNESCO shrines)",
          "Day 5 : Harajuku + Shibuya crossing + Meiji Shrine",
          "Day 6 : Mt. Fuji day trip from Tokyo",
          "Day 7 : Ginza shopping, farewell dinner, depart"
        });
  }

  @Override
  public String getName() {
    return "suggestTourPlans";
  }

  @Override
  public String getDescription() {
    return "Suggests curated multi-day tour plans for popular travel destinations. "
        + "Use this when the user wants trip ideas or itineraries rather than booking a specific trip.";
  }

  @Override
  public String getParameterDescription() {
    return "(no parameters required — returns curated plans for multiple destinations)";
  }

  /**
   * Prints curated tour plans to the console and records them in memory.
   *
   * @param params not required
   * @param memory agent memory; tourPlansSuggested flag will be set to true
   * @return successful ActionResult with all suggested plans
   */
  @Override
  public ActionResult execute(Map<String, String> x, AgentMemory memory) {
    StringBuilder display = new StringBuilder();
    System.out.println();
    System.out.println("  ╔══════════════════════════════════════════════════════════╗");
    System.out.println("  ║             ✈  SUGGESTED TOUR PLANS  ✈                  ║");
    System.out.println("  ╚══════════════════════════════════════════════════════════╝");

    for (Map.Entry<String, String[]> entry : TOUR_PLANS.entrySet()) {
      System.out.println();
      System.out.println("  📍 " + entry.getKey());
      System.out.println("  " + "─".repeat(55));
      for (String day : entry.getValue()) {
        System.out.println("    • " + day);
      }
      display.append(entry.getKey()).append(": ").append(entry.getValue().length).append(" days. ");
    }

    System.out.println();

    memory.setTourPlansSuggested(true);

    return ActionResult.success(
        "Suggested "
            + TOUR_PLANS.size()
            + " tour plans: "
            + display.toString().trim()
            + " User can now choose one to book, or the goal is complete if they only wanted suggestions.");
  }
}
