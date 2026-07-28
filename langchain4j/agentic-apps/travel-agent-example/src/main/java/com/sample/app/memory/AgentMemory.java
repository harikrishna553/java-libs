package com.sample.app.memory;

import com.sample.app.model.Flight;
import com.sample.app.model.Goal;
import com.sample.app.model.Hotel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Maintains the complete mutable state of the Travel Agent across all ReAct cycles.
 *
 * <p>AgentMemory is the single source of truth for what the agent knows at any point in time. It is
 * read by the Planning Module to build context-rich prompts, and written by the Orchestrator after
 * each action result is observed.
 *
 * <p>Key state tracked:
 *
 * <ul>
 *   <li>The current user {@link Goal}
 *   <li>A chronological list of observations (tool results in natural language)
 *   <li>Available flights and hotels discovered via search tools
 *   <li>The user-selected {@link Flight} and {@link Hotel}
 *   <li>Booking confirmation flags
 *   <li>Whether the confirmation email was sent
 * </ul>
 */
public class AgentMemory {

  private Goal goal;

  private final List<String> observations = new ArrayList<>();

  private List<Flight> availableFlights = new ArrayList<>();
  private List<Hotel> availableHotels = new ArrayList<>();

  private Flight selectedFlight;
  private Hotel selectedHotel;

  private boolean flightBooked;
  private boolean hotelBooked;
  private boolean emailSent;
  private boolean tourPlansSuggested;

  /**
   * Returns the current travel goal.
   *
   * @return the goal, or {@code null} if not yet set
   */
  public Goal getGoal() {
    return goal;
  }

  /**
   * Sets the travel goal. Called once by the Perception Module.
   *
   * @param goal the interpreted user goal
   */
  public void setGoal(Goal goal) {
    this.goal = goal;
  }

  /**
   * Appends a new observation to the memory.
   *
   * <p>Observations are produced by tools and written here by the Orchestrator. They are fed back
   * into every planning prompt so the LLM always has full context.
   *
   * @param observation a short natural-language summary of what happened
   */
  public void addObservation(String observation) {
    observations.add(observation);
  }

  /**
   * Returns an unmodifiable view of all recorded observations, in chronological order.
   *
   * @return list of observations
   */
  public List<String> getObservations() {
    return Collections.unmodifiableList(observations);
  }

  /**
   * Stores the list of flights returned by the Flight Search tool.
   *
   * @param flights available flights
   */
  public void setAvailableFlights(List<Flight> flights) {
    this.availableFlights = new ArrayList<>(flights);
  }

  /**
   * Returns the flights discovered by the most recent flight search.
   *
   * @return available flights (may be empty)
   */
  public List<Flight> getAvailableFlights() {
    return Collections.unmodifiableList(availableFlights);
  }

  /**
   * Stores the list of hotels returned by the Hotel Search tool.
   *
   * @param hotels available hotels
   */
  public void setAvailableHotels(List<Hotel> hotels) {
    this.availableHotels = new ArrayList<>(hotels);
  }

  /**
   * Returns the hotels discovered by the most recent hotel search.
   *
   * @return available hotels (may be empty)
   */
  public List<Hotel> getAvailableHotels() {
    return Collections.unmodifiableList(availableHotels);
  }

  /**
   * Records the flight chosen by the user.
   *
   * @param flight the user-selected flight
   */
  public void setSelectedFlight(Flight flight) {
    this.selectedFlight = flight;
  }

  /**
   * Returns the flight the user has chosen, or {@code null} if not yet selected.
   *
   * @return selected flight
   */
  public Flight getSelectedFlight() {
    return selectedFlight;
  }

  /**
   * Records the hotel chosen by the user.
   *
   * @param hotel the user-selected hotel
   */
  public void setSelectedHotel(Hotel hotel) {
    this.selectedHotel = hotel;
  }

  /**
   * Returns the hotel the user has chosen, or {@code null} if not yet selected.
   *
   * @return selected hotel
   */
  public Hotel getSelectedHotel() {
    return selectedHotel;
  }

  /**
   * Marks the selected flight as successfully booked.
   *
   * @param booked {@code true} once booking is confirmed
   */
  public void setFlightBooked(boolean booked) {
    this.flightBooked = booked;
  }

  /**
   * Returns whether the flight has been booked.
   *
   * @return {@code true} if the flight booking is confirmed
   */
  public boolean isFlightBooked() {
    return flightBooked;
  }

  /**
   * Marks the selected hotel as successfully booked.
   *
   * @param booked {@code true} once booking is confirmed
   */
  public void setHotelBooked(boolean booked) {
    this.hotelBooked = booked;
  }

  /**
   * Returns whether the hotel has been booked.
   *
   * @return {@code true} if the hotel booking is confirmed
   */
  public boolean isHotelBooked() {
    return hotelBooked;
  }

  /**
   * Marks the confirmation email as sent.
   *
   * @param sent {@code true} once the email has been dispatched
   */
  public void setEmailSent(boolean sent) {
    this.emailSent = sent;
  }

  /**
   * Returns whether the confirmation email has been sent.
   *
   * @return {@code true} if the email was sent
   */
  public boolean isEmailSent() {
    return emailSent;
  }

  /**
   * Marks tour plans as suggested to the user.
   *
   * @param suggested {@code true} once plans have been presented
   */
  public void setTourPlansSuggested(boolean suggested) {
    this.tourPlansSuggested = suggested;
  }

  /**
   * Returns whether tour plans have been suggested to the user.
   *
   * @return {@code true} if plans were already suggested
   */
  public boolean isTourPlansSuggested() {
    return tourPlansSuggested;
  }

  /**
   * Returns {@code true} when the full booking workflow is complete: flight booked, hotel booked,
   * and confirmation email sent.
   *
   * <p>The Orchestrator uses this for a deterministic exit check so the agent loop does not depend
   * solely on the LLM signalling COMPLETED.
   *
   * @return true if all booking tasks are done
   */
  public boolean isBookingWorkflowComplete() {
    return flightBooked && hotelBooked && emailSent;
  }

  /**
   * Produces a compact state summary for use inside the LLM planning prompt.
   *
   * <p>This string is injected into every planning prompt so the LLM knows exactly what has already
   * been accomplished without re-reading the full observation history.
   *
   * @return multiline status summary
   */
  public String getStateSummary() {
    StringBuilder sb = new StringBuilder();
    sb.append("Flights searched    : ")
        .append(availableFlights.isEmpty() ? "NO" : "YES (" + availableFlights.size() + " found)")
        .append("\n");
    sb.append("Flight selected     : ")
        .append(selectedFlight != null ? selectedFlight.display() : "NO")
        .append("\n");
    sb.append("Flight booked       : ").append(flightBooked ? "YES" : "NO").append("\n");
    sb.append("Hotels searched     : ")
        .append(availableHotels.isEmpty() ? "NO" : "YES (" + availableHotels.size() + " found)")
        .append("\n");
    sb.append("Hotel selected      : ")
        .append(selectedHotel != null ? selectedHotel.display() : "NO")
        .append("\n");
    sb.append("Hotel booked        : ").append(hotelBooked ? "YES" : "NO").append("\n");
    sb.append("Confirmation email  : ").append(emailSent ? "SENT" : "NOT SENT").append("\n");
    sb.append("Tour plans suggested: ").append(tourPlansSuggested ? "YES" : "NO").append("\n");
    return sb.toString();
  }
}
