package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;

/**
 * Service class for movie operations.
 * Note: Most movie operations are handled directly in MovieController.
 * This service is kept for potential future business logic.
 */
public class MovieService {

    /**
     * Displays all movies (for backward compatibility).
     */
    public static void viewMovies() {
        System.out.println("\n--- AVAILABLE MOVIES ---");
        System.out.println("(Use MovieController for full functionality)");
    }
}
