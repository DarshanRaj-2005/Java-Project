package org.expleo.TicketBookingJavaProject.service;

import java.util.UUID;
import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.exception.BookingNotFoundException;

/**
 * Service class for booking operations.
 * Handles business logic for ticket bookings.
 */
public class BookingService {
    
    // Repository for seat operations
    private static SeatRepositoryImpl seatRepo = new SeatRepositoryImpl();

    /**
     * Generates a unique booking ID.
     * @return Random 8-character booking ID
     */
    public String generateBookingId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Calculates total price for booking.
     * @param seatCount Number of seats
     * @param pricePerSeat Price per seat
     * @return Total amount
     */
    public double calculateTotalPrice(int seatCount, double pricePerSeat) {
        return seatCount * pricePerSeat;
    }

    /**
     * Confirms a booking by saving it to the database.
     * @param booking Booking object to confirm
     */
    public void confirmBooking(Booking booking) {
        booking.setStatus("CONFIRMED");
        BookingRepositoryImpl.addBooking(booking);
        System.out.println("Booking " + booking.getBookingId() + " confirmed successfully!");
    }

    /**
     * Cancels a booking and restores seats.
     * @param bookingId ID of the booking to cancel
     * @throws BookingNotFoundException if booking not found
     */
    public void cancelBooking(String bookingId) {
        Booking booking = BookingRepositoryImpl.getBookingById(bookingId);
        
        if (booking == null) {
            throw new BookingNotFoundException("Booking with ID " + bookingId + " not found!");
        }

        // Build session key to restore seats
        String sessionKey = booking.getTheatreId() + "_" + booking.getMovieId() + "_" + 
                           booking.getShowtime().replace(" ", "_").replace(":", "");

        // Restore seats to AVAILABLE
        for (String label : booking.getSeatLabels()) {
            Seat seat = findSeatByLabel(sessionKey, label);
            if (seat != null) {
                seat.setStatus("AVAILABLE");
                seatRepo.updateSeat(seat);
            }
        }

        // Update booking status to CANCELLED in database
        BookingRepositoryImpl.deleteBooking(bookingId);
        System.out.println("Booking " + bookingId + " cancelled and seats restored.");
    }

    /**
     * Finds a seat by its label in a session.
     * @param sessionKey Session identifier
     * @param label Seat label (e.g., "A1")
     * @return Seat object if found, null otherwise
     */
    private Seat findSeatByLabel(String sessionKey, String label) {
        for (Seat s : seatRepo.getSeatsForSession(sessionKey)) {
            if (s.getSeatLabel().equalsIgnoreCase(label)) {
                return s;
            }
        }
        return null;
    }
}
