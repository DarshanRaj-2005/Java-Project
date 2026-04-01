package org.expleo.TicketBookingJavaProject.service;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

/**
 * Service class for seat-related operations. Handles business logic for seat
 * management.
 */
public class SeatService {

	// Repository for seat database operations
	private static SeatRepositoryImpl repo = new SeatRepositoryImpl();

	/**
	 * Gets complete seat layout for a session.
	 * 
	 * @param sessionKey Session identifier
	 * @return List of all seats
	 */
	public List<Seat> getSeatLayout(String sessionKey) {
		return repo.getSeatsForSession(sessionKey);
	}

	/**
	 * Gets only available seats for a session.
	 * 
	 * @param sessionKey Session identifier
	 * @return List of available seats
	 */
	public List<Seat> getAvailableSeats(String sessionKey) {
		List<Seat> available = new ArrayList<>();
		for (Seat s : repo.getSeatsForSession(sessionKey)) {
			if (s.getStatus().equalsIgnoreCase("AVAILABLE")) {
				available.add(s);
			}
		}
		return available;
	}

	/**
	 * Gets only booked seats for a session.
	 * 
	 * @param sessionKey Session identifier
	 * @return List of booked seats
	 */
	public List<Seat> getBookedSeats(String sessionKey) {
		List<Seat> booked = new ArrayList<>();
		for (Seat s : repo.getSeatsForSession(sessionKey)) {
			if (s.getStatus().equalsIgnoreCase("BOOKED")) {
				booked.add(s);
			}
		}
		return booked;
	}

	/**
	 * Gets a specific seat by its label.
	 * 
	 * @param sessionKey Session identifier
	 * @param label      Seat label (e.g., "A1")
	 * @return Seat object if found, null otherwise
	 */
	public Seat getSeatByLabel(String sessionKey, String label) {
		for (Seat s : repo.getSeatsForSession(sessionKey)) {
			if (s.getSeatLabel().equalsIgnoreCase(label)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Validates a single seat selection.
	 * 
	 * @param sessionKey Session identifier
	 * @param label      Seat label to validate
	 * @return "VALID" if valid, error message otherwise
	 */
	public String validateSingleSeatSelection(String sessionKey, String label) {
		Seat seat = getSeatByLabel(sessionKey, label);

		if (seat == null) {
			return "Error: Seat '" + label + "' does not exist.";
		}

		if (seat.getStatus().equalsIgnoreCase("BOOKED")) {
			return "Error: Seat '" + label + "' is already booked.";
		}

		return "VALID";
	}

	/**
	 * Validates multiple seat selections.
	 * 
	 * @param sessionKey Session identifier
	 * @param labels     List of seat labels
	 * @param count      Expected number of seats
	 * @return "VALID" if valid, error message otherwise
	 */
	public String validateMultipleSeatSelection(String sessionKey, List<String> labels, int count) {
		// Check count matches
		if (labels.size() != count) {
			return "Error: Please select exactly " + count + " seats.";
		}

		// Check for duplicates
		Set<String> uniqueSeats = new HashSet<>(labels);
		if (uniqueSeats.size() != labels.size()) {
			return "Error: You have selected duplicate seats.";
		}

		// Validate each seat
		for (String label : labels) {
			String result = validateSingleSeatSelection(sessionKey, label);
			if (!result.equals("VALID")) {
				return result;
			}
		}

		return "VALID";
	}

	/**
	 * Updates seat information in the database.
	 * 
	 * @param seat Seat object with updated information
	 */
	public void updateSeat(Seat seat) {
		repo.updateSeat(seat);
	}
}
