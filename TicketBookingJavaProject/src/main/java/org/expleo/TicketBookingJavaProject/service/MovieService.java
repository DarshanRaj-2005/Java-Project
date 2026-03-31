package org.expleo.TicketBookingJavaProject.service;

import java.util.ArrayList;
import java.util.List;

import org.expleo.TicketBookingJavaProject.model.Movie;

/*
 * MovieService Class
 * Handles operations related to movies
 * Acts as a simple in-memory service for managing movie data
 */
public class MovieService {

    // Static list to store all movies (acts like a database)
    public static List<Movie> movieList = new ArrayList<>();

    /*
     * Displays all available movies
     */
    public static void viewMovies() {

        // Check if movie list is empty
        if (MovieService.movieList.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        // Display header
        System.out.println("Available Movies:");

        // Loop through movie list and print titles
        for (Movie m : MovieService.movieList) {
            System.out.println(m.getTitle());
        }
    }
}