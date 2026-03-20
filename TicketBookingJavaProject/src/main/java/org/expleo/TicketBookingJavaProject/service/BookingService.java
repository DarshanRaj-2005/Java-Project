package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;

import java.util.List;

public class BookingService {

    public List<City> getCities() {
        return BookingRepositoryImpl.cities;
    }

    public List<Theatre> getTheatres() {
        return BookingRepositoryImpl.theatres;
    }

    public List<Movie> getMovies() {
        return BookingRepositoryImpl.movies;
    }

    public List<Showtime> getShowtimes() {
        return BookingRepositoryImpl.showtimes;
    }

    public void displayCities(List<City> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getName());
        }
    }

    public void displayTheatres(List<Theatre> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getName());
        }
    }

    public void displayMovies(List<Movie> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getTitle());
        }
    }

    public void displayShowtimes(List<Showtime> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getTime());
        }
    }
}