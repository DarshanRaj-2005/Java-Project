package org.expleo.TicketBookingJavaProject.service;

import java.util.ArrayList;
import java.util.List;

import org.expleo.TicketBookingJavaProject.model.City;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Showtime;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;

// Service class to handle selection and filtering logic
public class SelectionService {

    // ==================== GET METHODS ====================

    // Get all cities
    public List<City> getCities() {
        return BookingRepositoryImpl.getCities();
    }

    // Get theatres based on selected city
    public List<Theatre> getTheatresByCity(City city) {
        List<Theatre> result = new ArrayList<>();

        for (Theatre t : BookingRepositoryImpl.getTheatres()) {
            // Assuming each theatre belongs to a city (future upgrade)
            // For now, return all (you can link later)
            result.add(t);
        }

        return result;
    }

    // Get movies available in a selected theatre
    public List<Movie> getMoviesByTheatre(Theatre theatre) {
        // Using theatre object directly (better design)
        return theatre.getMovies();
    }

    // Get showtimes based on movie and theatre
    public List<Showtime> getShowtimes(Movie movie, Theatre theatre) {
        List<Showtime> result = new ArrayList<>();

        for (Showtime s : BookingRepositoryImpl.getShowtimes()) {
            // Filter based on movie and theatre
            if (s.getMovie().getId() == movie.getId() &&
                s.getTheatre().getId() == theatre.getId()) {

                result.add(s);
            }
        }

        return result;
    }

    // ==================== DISPLAY METHODS ====================

    // Display cities
    public void displayCities(List<City> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    // Display theatres
    public void displayTheatres(List<Theatre> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    // Display movies
    public void displayMovies(List<Movie> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    // Display showtimes
    public void displayShowtimes(List<Showtime> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }
}