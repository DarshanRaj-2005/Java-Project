package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Booking;

/**
 * Data Access Object interface for Booking operations. Defines the contract for
 * booking-related database operations.
 */
public interface BookingDAO {

	/**
	 * Retrieves all bookings from the database.
	 * 
	 * @return List of all bookings
	 */
	List<Booking> getAllBookings();

	/**
	 * Retrieves a booking by its ID.
	 * 
	 * @param bookingId The unique identifier of the booking
	 * @return Booking object if found, null otherwise
	 */
	Booking getBookingById(String bookingId);

	/**
	 * Adds a new booking to the database.
	 * 
	 * @param booking The booking object to add
	 */
	void addBooking(Booking booking);

	/**
	 * Updates an existing booking in the database.
	 * 
	 * @param booking The booking object with updated information
	 */
	void updateBooking(Booking booking);

	/**
	 * Deletes a booking from the database.
	 * 
	 * @param bookingId The ID of the booking to delete
	 */
	void deleteBooking(String bookingId);

	/**
	 * Cancels a booking by updating its status.
	 * 
	 * @param bookingId The ID of the booking to cancel
	 */
	void cancelBooking(String bookingId);
}
