/*
 * FILE: BookingService.java
 * PURPOSE: Handles booking business logic.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Abstraction: Hides booking complexity
 * - Composition: Uses SeatRepositoryImpl and BookingRepositoryImpl
 * 
 * WHAT THIS FILE DOES:
 * - Generates unique booking IDs
 * - Confirms bookings
 * - Cancels bookings
 * - Restores seats when booking is cancelled
 */


//------------Author Name: Rohini, Tamil Kumar---------------



package org.expleo.TicketBookingJavaProject.service;

import java.util.UUID;

import org.expleo.TicketBookingJavaProject.exception.BookingNotFoundException;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

/*
 * Service class for booking operations.
 * Handles business logic for ticket bookings.
 */
public class BookingService {
    
    private SeatRepositoryImpl seatDAO = new SeatRepositoryImpl();
    private BookingRepositoryImpl bookingDAO = new BookingRepositoryImpl();

    /*
     * generateBookingId - Creates a unique ID for each booking
     * 
     * Returns: 8-character random string (uppercase)
     * Example: "A1B2C3D4"
     */
    public String generateBookingId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /*
     * calculateTotalPrice - Calculates price for tickets
     * 
     * Parameters:
     * - seatCount: Number of tickets
     * - pricePerSeat: Price for each seat
     * 
     * Returns: Total price
     */
    public double calculateTotalPrice(int seatCount, double pricePerSeat) {
        return seatCount * pricePerSeat;
    }

    /*
     * confirmBooking - Saves booking to database
     * 
     * Sets status to "CONFIRMED" and saves to database.
     */
    public void confirmBooking(Booking booking) {
        booking.setStatus("CONFIRMED");
        bookingDAO.addBooking(booking);
        System.out.println("Booking " + booking.getBookingId() + " confirmed successfully!");
    }

    /*
     * cancelBooking - Cancels a booking and restores seats
     * 
     * Steps:
     * 1. Find the booking
     * 2. Make seats available again
     * 3. Update booking status to CANCELLED
     */
    public void cancelBooking(String bookingId) {
        Booking booking = bookingDAO.getBookingById(bookingId);
        
        if (booking == null) {
            throw new BookingNotFoundException("Booking with ID " + bookingId + " not found!");
        }

        // Build session key to find seats
        String sessionKey = booking.getTheatreId() + "_" + booking.getMovieId() + "_" + 
                           booking.getShowtime().replace(" ", "_").replace(":", "");

        // Make seats available again
        for (String label : booking.getSeatLabels()) {
            Seat seat = findSeatByLabel(sessionKey, label);
            if (seat != null) {
                seat.setStatus("AVAILABLE");
                seatDAO.updateSeat(seat);
            }
        }

        // Delete booking from database
        bookingDAO.deleteBooking(bookingId);
        System.out.println("Booking " + bookingId + " cancelled and seats restored.");
    }

    /*
     * findSeatByLabel - Finds a seat by its label
     * 
     * Parameters:
     * - sessionKey: theatre_movie_showtime combination
     * - label: Seat label like "A1"
     * 
     * Returns: Seat object or null if not found
     */
    private Seat findSeatByLabel(String sessionKey, String label) {
        for (Seat s : seatDAO.getSeatsForSession(sessionKey)) {
            if (s.getSeatLabel().equalsIgnoreCase(label)) {
                return s;
            }
        }
        return null;
    }
}
