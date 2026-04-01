package org.expleo.TicketBookingJavaProject.service;

import java.util.List;
<<<<<<< HEAD

import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class BookingService {

	private SeatRepositoryImpl repo = new SeatRepositoryImpl();

	public boolean validateTicketCount(int ticketCount) {

		if (ticketCount <= 0) {
			return false;
		}

		List<Seat> seats = repo.getSeats();
		int availableSeats = 0;

		for (Seat seat : seats) {
			if (seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
				availableSeats++;
			}
		}

		return ticketCount <= availableSeats;
	}
	public void confirmBooking(Booking booking, Payment payment) {

	    if (payment.getStatus().equalsIgnoreCase("SUCCESS")) {
	        booking.setStatus("CONFIRMED");
	        System.out.println("Booking Confirmed!");
	    } else {
	        booking.setStatus("FAILED");
	        System.out.println("Booking Failed due to payment.");
	    }
	}
}
=======
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;
import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.model.Booking;

/*
 * BookingService
 * Handles all booking-related business logic
 * - Validates ticket availability
 * - Confirms booking based on payment
 * - Updates seat status (BOOKED)
 */
public class BookingService {

    // Repository instance to access seat data
    private static SeatRepositoryImpl repo = new SeatRepositoryImpl();

    /*
     * Validates if requested ticket count is available
     * @param ticketCount Number of tickets requested
     * @return true if tickets can be booked
     */
    public boolean validateTicketCount(int ticketCount) {

        // Reject invalid ticket count
        if (ticketCount <= 0) return false;

        int availableSeats = 0;

        // Loop through all seats and count AVAILABLE ones
        for (Seat seat : repo.getSeats()) {
            if (seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
                availableSeats++;
            }
        }

        // Check if enough seats are available
        return ticketCount <= availableSeats;
    }

    /*
     * Confirms booking after payment
     * @param booking Booking object
     * @param payment Payment details
     */
    public void confirmBooking(Booking booking, Payment payment) {

        // Check payment status
        if (payment.getStatus().equalsIgnoreCase("SUCCESS")) {

            // Update booking status
            booking.setStatus("CONFIRMED");
            System.out.println("Booking Confirmed!");

        } else {

            // Mark booking as failed
            booking.setStatus("FAILED");
            System.out.println("Booking Failed!");
        }
    }

    /*
     * Books seats automatically (first available seats)
     * @param count Number of seats to book
     */
    public void bookSeats(int count) {

        List<Seat> seats = SeatRepositoryImpl.getSeats();
        int booked = 0;

        // Loop through seats and mark AVAILABLE seats as BOOKED
        for (Seat seat : seats) {

            if (seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
                seat.setStatus("BOOKED");
                booked++;
            }

            // Stop when required seats are booked
            if (booked == count) break;
        }

        // Display confirmation
        System.out.println(booked + " Seats Booked Successfully!");
    }
}
