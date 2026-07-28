package com.sample.app.tools;

import com.sample.app.memory.AgentMemory;
import com.sample.app.model.ActionResult;
import com.sample.app.model.Flight;
import com.sample.app.model.Hotel;
import java.util.Map;

/**
 * Mock tool that sends a booking confirmation email to the user.
 *
 * <p>In production this would call an email API (SendGrid, AWS SES, etc.). In this educational demo
 * it simply prints the email content to the console and marks the email as sent in {@link
 * AgentMemory}.
 */
public class EmailTool implements Tool {

  @Override
  public String getName() {
    return "sendEmail";
  }

  @Override
  public String getDescription() {
    return "Sends a booking confirmation email summarizing the booked flight and hotel.";
  }

  @Override
  public String getParameterDescription() {
    return "(no parameters required - reads booking details from memory)";
  }

  /**
   * Prints a mock confirmation email and marks it as sent in memory.
   *
   * @param params not required; booking details come from memory
   * @param memory must contain booked flight and hotel information
   * @return successful ActionResult confirming the email was sent
   */
  @Override
  public ActionResult execute(Map<String, String> params, AgentMemory memory) {
    Flight flight = memory.getSelectedFlight();
    Hotel hotel = memory.getSelectedHotel();

    System.out.println();
    System.out.println("  ╔══════════════════════════════════════════════════╗");
    System.out.println("  ║              📧 EMAIL SENT                       ║");
    System.out.println("  ╠══════════════════════════════════════════════════╣");
    System.out.println("  ║  To      : traveller@example.com                 ║");
    System.out.println("  ║  Subject : Your Trip to London — Confirmed!      ║");
    System.out.println("  ╠══════════════════════════════════════════════════╣");
    System.out.println("  ║  Dear Traveller,                                 ║");
    System.out.println("  ║                                                  ║");
    System.out.println("  ║  Your trip has been confirmed:                   ║");

    if (flight != null) {
      System.out.printf("  ║  ✈ Flight : %-38s║%n", flight.display());
    }
    if (hotel != null) {
      System.out.printf("  ║  🏨 Hotel  : %-38s║%n", hotel.display());
    }

    System.out.println("  ║                                                  ║");
    System.out.println("  ║  Have a great trip!                              ║");
    System.out.println("  ║  — Travel Agent AI                               ║");
    System.out.println("  ╚══════════════════════════════════════════════════╝");
    System.out.println();

    memory.setEmailSent(true);

    return ActionResult.success(
        "Confirmation email sent to traveller@example.com with full booking details.");
  }
}
