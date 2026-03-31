package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;

/*
 * SearchController
 * Handles searching and selecting movies from a given list
 * Acts as UI layer for movie search functionality
 */
public class SearchController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    // List of movie names (data source)
    private List<String> movies;

    /*
     * Constructor to initialize movie list
     * @param movies List of available movie names
     */
    public SearchController(List<String> movies) {
        this.movies = movies;
    }

    /*
     * Allows user to search and select a movie
     * @return selected movie name or null if not found
     */
    public String searchMovie() {

        // Take search input from user
        System.out.print("Enter movie name: ");
        String search = sc.nextLine().toLowerCase();

        // List to store matching results
        List<String> results = new ArrayList<>();

        // Search movies (case-insensitive)
        for (String movie : movies) {
            if (movie.toLowerCase().contains(search)) {
                results.add(movie);
            }
        }

        // If no results found
        if (results.isEmpty()) {
            System.out.println("No movies found.");
            return null;
        }

        // Display search results
        System.out.println("\nSearch Results:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        // Variable to store user choice
        int choice = -1;

        /*
         * Loop until valid input is given
         */
        while (true) {

            System.out.print("Select movie (number): ");

            // Check if input is integer
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                // Validate range
                if (choice >= 1 && choice <= results.size()) {
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }

            } else {
                // Handle non-integer input
                System.out.println("Please enter a valid number.");
                sc.next(); // clear invalid input
            }
        }

        // Return selected movie
        return results.get(choice - 1);
    }
}