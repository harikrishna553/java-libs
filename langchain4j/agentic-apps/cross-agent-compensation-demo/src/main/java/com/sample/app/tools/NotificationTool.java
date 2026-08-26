package com.sample.app.tools;

import dev.langchain4j.agent.tool.Tool;

public class NotificationTool {

  /**
   * Controls whether this tool simulates a failure. true = throws an exception (triggers
   * compensation) false = succeeds normally
   */
  private boolean simulateFailure = false;

  public void setSimulateFailure(boolean simulateFailure) {
    this.simulateFailure = simulateFailure;
  }

  public boolean isSimulateFailure() {
    return simulateFailure;
  }

  /**
   * Simulates sending a transfer notification. Throws when {@code simulateFailure} is true, which
   * causes the workflow to trigger compensation.
   */
  @Tool("Send a transfer completion notification to the user")
  public String sendNotification(String user, String message) {

    System.out.println();
    System.out.println("[NOTIFICATION] Sending notification to " + user);
    System.out.println("[NOTIFICATION] Message: " + message);

    if (simulateFailure) {
      System.out.println("[NOTIFICATION] *** Simulating service failure ***");
      throw new RuntimeException("Notification service unavailable");
    }

    System.out.println("[NOTIFICATION] Notification sent successfully.");
    return "Notification delivered to " + user;
  }
}
