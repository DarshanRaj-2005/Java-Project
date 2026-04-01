package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;

/**
 * Data Access Object interface for Seat operations. Defines the contract for
 * seat-related database operations.
 */
public interface SeatDAO {

	/**
	 * Retrieves all seats for a specific session.
	 * 
	 * @param sessionKey The session identifier (format: theatre_movie_showtime)
	 * @return List of all seats for the session
	 */
	List<Seat> getSeatsForSession(String sessionKey);

	/**
	 * Retrieves a seat by its label within a session.
	 * 
	 * @param sessionKey The session identifier
	 * @param seatLabel  The seat label (e.g., "A1", "B5")
	 * @return Seat object if found, null otherwise
	 */
	Seat getSeatByLabel(String sessionKey, String seatLabel);

	/**
	 * Updates a seat's information in the database.
	 * 
	 * @param seat The seat object with updated information
	 */
	void updateSeat(Seat seat);

	/**
	 * Initializes seats for a new session.
	 * 
	 * @param sessionKey The session identifier
	 */
	void initializeSeats(String sessionKey);
}
