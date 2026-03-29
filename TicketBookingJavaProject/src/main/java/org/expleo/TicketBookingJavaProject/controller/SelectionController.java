package org.expleo.TicketBookingJavaProject.controller;

import java.util.List;
import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.model.City;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Showtime;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.service.SelectionService;

public class SelectionController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    // Service layer object for business logic
    SelectionService service = new SelectionService();

    // Main booking flow (City → Theatre → Movie → Showtime)
    public void startBookingFlow() {

        // ==================== STEP 1: CITY SELECTION ====================
        
        // Fetch all cities from service
        List<City> cities = service.getCities();

        // Check if city list is empty
        if (cities.isEmpty()) {
            System.out.println("No cities available. Admin must add data.");
            return;
        }

        // Display cities to user
        System.out.println("Select City:");
        service.displayCities(cities);

        // Get valid user input
        int cityChoice = getValidChoice(cities.size());

        // Get selected city object
        City selectedCity = cities.get(cityChoice - 1);

        // ==================== STEP 2: THEATRE SELECTION ====================
        
        // Fetch theatres based on selected city
        List<Theatre> theatres = service.getTheatresByCity(selectedCity);

        // Check if theatres exist
        if (theatres.isEmpty()) {
            System.out.println("No theatres available for selected city.");
            return;
        }

        // Display theatres
        System.out.println("\nSelect Theatre:");
        service.displayTheatres(theatres);

        // Get valid input
        int theatreChoice = getValidChoice(theatres.size());

        // Get selected theatre
        Theatre selectedTheatre = theatres.get(theatreChoice - 1);

        // ==================== STEP 3: MOVIE SELECTION ====================
        
        // Fetch movies available in selected theatre
        List<Movie> movies = service.getMoviesByTheatre(selectedTheatre);

        // Check if movies exist
        if (movies == null || movies.isEmpty()) {
            System.out.println("No movies available for selected theatre.");
            return;
        }

        // Display movies
        System.out.println("\nSelect Movie:");
        service.displayMovies(movies);

        // Get valid input
        int movieChoice = getValidChoice(movies.size());

        // Get selected movie
        Movie selectedMovie = movies.get(movieChoice - 1);

        // ==================== STEP 4: SHOWTIME SELECTION ====================
        
        // Fetch showtimes based on movie and theatre
        List<Showtime> shows = service.getShowtimes(selectedMovie, selectedTheatre);

        // Check if showtimes exist
        if (shows.isEmpty()) {
            System.out.println("No showtimes available for selected movie.");
            return;
        }

        // Display showtimes
        System.out.println("\nSelect Showtime:");
        service.displayShowtimes(shows);

        // Get valid input
        int showChoice = getValidChoice(shows.size());

        // Get selected showtime
        Showtime selectedShow = shows.get(showChoice - 1);

        // ==================== FINAL SUMMARY ====================
        
        // Display selected booking details
        System.out.println("\n===== BOOKING SUMMARY =====");
        System.out.println("City: " + selectedCity.getName());
        System.out.println("Theatre: " + selectedTheatre.getName());
        System.out.println("Movie: " + selectedMovie.getTitle());
        System.out.println("Showtime: " + selectedShow.getTime());
    }

    // 🔥 Reusable method to safely get valid input from user
    private int getValidChoice(int size) {
        int choice;

        while (true) {
            System.out.print("Enter choice (1-" + size + "): ");

            // Check if input is integer
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                // Validate range
                if (choice >= 1 && choice <= size) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                // Handle invalid (non-integer) input
                System.out.println("Invalid input. Enter a number.");
                sc.next(); // clear invalid input
            }
        }
    }
}