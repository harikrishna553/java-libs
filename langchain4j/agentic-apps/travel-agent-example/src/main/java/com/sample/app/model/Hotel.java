package com.sample.app.model;

/**
 * Immutable representation of a hotel option returned by the Hotel Search tool.
 *
 * <p>Hotel data is mocked; no real hotel API is called.
 *
 * @param id Unique hotel identifier (e.g., "H-001")
 * @param name Hotel name (e.g., "The Savoy")
 * @param location City and area description (e.g., "London, Strand")
 * @param stars Star rating (1–5)
 * @param pricePerNight Nightly rate in USD
 */
public record Hotel(String id, String name, String location, int stars, double pricePerNight) {

  /**
   * Returns a compact display string suitable for console output.
   *
   * @return formatted hotel description
   */
  public String display() {
    return String.format(
        "[%s] %s | %s | %d★ | $%.2f/night", id, name, location, stars, pricePerNight);
  }
}
