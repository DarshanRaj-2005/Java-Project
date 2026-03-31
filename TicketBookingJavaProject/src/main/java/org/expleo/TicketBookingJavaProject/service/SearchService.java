package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;

import java.util.*;

// Service class to handle movie search operations
public class SearchService {

    // Method to search movies by title (partial match)
    public List<Movie> searchByTitle(String title) {

        // List to store matching results
        List<Movie> result = new ArrayList<>();

        // Get movie list from repository (fixed: using getter)
        for (Movie m : BookingRepositoryImpl.getMovies()) {

            // Check if movie title contains input (case-insensitive)
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }

    // Method to search movies by language (exact match, case-insensitive)
    public List<Movie> searchByLanguage(String language) {

        // List to store matching results
        List<Movie> result = new ArrayList<>();

        // Get movie list from repository (fixed: using getter)
        for (Movie m : BookingRepositoryImpl.getMovies()) {

            // Check if language matches
            if (m.getLanguage().equalsIgnoreCase(language)) {
                result.add(m);
            }
        }
        return result;
    }

    // Method to display list of movies
    public void displayMovies(List<Movie> list) {

        // Check if list is empty
        if (list.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        // Display movies with numbering
        for (int i = 0; i < list.size(); i++) {
            Movie m = list.get(i);

            // Using getters to display details
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ")");
        }
    }
}