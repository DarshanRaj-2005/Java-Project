package org.expleo.TicketBookingJavaProject.repository.impl;

import java.util.List;
import java.util.ArrayList;
import org.expleo.TicketBookingJavaProject.model.*;

// Repository class acting as an in-memory database
// Stores and manages all application data
public class BookingRepositoryImpl {

    // List to store all cities (private for encapsulation)
    private static List<City> cities = new ArrayList<>();

    // List to store all theatres
    private static List<Theatre> theatres = new ArrayList<>();

    // List to store all movies
    private static List<Movie> movies = new ArrayList<>();

    // List to store all showtimes
    private static List<Showtime> showtimes = new ArrayList<>();

    // ==================== GET METHODS ====================

    // Returns list of all cities
    public static List<City> getCities() {
        return cities;
    }

    // Returns list of all theatres
    public static List<Theatre> getTheatres() {
        return theatres;
    }

    // Returns list of all movies
    public static List<Movie> getMovies() {
        return movies;
    }

    // Returns list of all showtimes
    public static List<Showtime> getShowtimes() {
        return showtimes;
    }

    // ==================== ADD METHODS ====================

    // Adds a new city to the list
    public static void addCity(City city) {
        cities.add(city);
    }

    // Adds a new theatre to the list
    public static void addTheatre(Theatre theatre) {
        theatres.add(theatre);
    }

    // Adds a new movie to the list
    public static void addMovie(Movie movie) {
        movies.add(movie);
    }

    // Adds a new showtime to the list
    public static void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }
}