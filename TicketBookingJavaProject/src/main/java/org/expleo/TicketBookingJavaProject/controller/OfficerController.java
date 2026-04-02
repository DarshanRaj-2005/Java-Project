package org.expleo.TicketBookingJavaProject.controller;

/**
 * Controller for Officer operations.
 * Officers can view movies and manage bookings.
 */
public class OfficerController {

    // Reference to MovieController
    private MovieController movieController;
    
    // Reference to BookingController
    private BookingController bookingController;

    /**
     * Constructor to initialize dependencies.
     */
    public OfficerController(MovieController movieController, BookingController bookingController) {
        this.movieController = movieController;
        this.bookingController = bookingController;
    }

    /**
     * Views all available movies.
     */
    public void viewMovies() {
        movieController.viewMovies();
    }

    /**
     * Starts the booking process for officers.
     */
    public void bookTicket() {
        System.out.println("\n--- BOOKING TICKET ---");
        bookingController.startBooking(-1);
    }

    /**
     * Cancels an existing booking.
     */
    public void cancelTicket() {
        bookingController.cancelBooking();
    }
}
