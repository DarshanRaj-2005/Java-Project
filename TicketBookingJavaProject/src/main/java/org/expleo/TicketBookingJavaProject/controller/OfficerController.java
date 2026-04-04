package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;

/**
 * Controller for Officer operations.
 * Officers can view movies and manage bookings for their assigned theatre.
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
     * Views movies available in the officer's theatre.
     */
    public void viewMovies(User officerUser) {
        Theatre theatre = TheatreRepositoryImpl.getTheatreById(officerUser.getTheatreId());
        if (theatre != null) {
            System.out.println("\nMovies at " + theatre.getName() + " (" + theatre.getCity() + "):");
            movieController.viewMoviesForTheatre(theatre.getId());
        } else {
            System.out.println("Error: No theatre assigned to you!");
        }
    }

    /**
     * Views all available movies (for general viewing).
     */
    public void viewAllMovies() {
        movieController.viewMovies();
    }

    /**
     * Starts the booking process for officers.
     * Officers can only book tickets for their assigned theatre.
     */
    public void bookTicket(User officerUser) {
        Theatre theatre = TheatreRepositoryImpl.getTheatreById(officerUser.getTheatreId());
        
        if (theatre == null) {
            System.out.println("\nError: You are not assigned to any theatre!");
            System.out.println("Please contact your Theatre Admin.");
            return;
        }
        
        System.out.println("\n--- BOOKING TICKET ---");
        System.out.println("Booking for: " + theatre.getName() + " (" + theatre.getCity() + ")");
        
        // Book ticket for the officer's assigned theatre only
        bookingController.startBookingForTheatre(theatre, officerUser.getUserId());
    }

    /**
     * Cancels an existing booking.
     */
    public void cancelTicket() {
        // Officers can cancel any booking
        bookingController.cancelBooking(0);
    }
}
