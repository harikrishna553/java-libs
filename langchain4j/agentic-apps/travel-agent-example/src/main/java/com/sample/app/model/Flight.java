package com.sample.app.model;

/**
 * Immutable representation of a flight option returned by the Flight Search tool.
 *
 * <p>Flight data is mocked; no real flight API is called.
 *
 * @param id Unique flight identifier (e.g., "BA-101")
 * @param airline Airline name (e.g., "British Airways")
 * @param from Departure airport code (e.g., "JFK")
 * @param to Arrival airport code (e.g., "LHR")
 * @param date Departure date as a human-readable string (e.g., "2025-07-28")
 * @param price Ticket price in USD
 */
public record Flight(String id, String airline, String from, String to, String date, double price) {

  /**
   * Returns a compact display string suitable for console output.
   *
   * @return formatted flight description
   */
  public String display() {
    return String.format("[%s] %s | %s → %s | %s | $%.2f", id, airline, from, to, date, price);
  }
}
