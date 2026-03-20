package org.expleo.TicketBookingJavaProject.service;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;
import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;

public class BookingService {

	private SeatRepositoryImpl repo = new SeatRepositoryImpl();

	public boolean validateTicketCount(int ticketCount) {

		List<Seat> seats = repo.getSeats();

		int availableSeats = 0;

		for (Seat seat : seats) {
			if (seat.getStatus().equals("AVAILABLE")) {
				availableSeats++;
			}
		}

		return ticketCount <= availableSeats;
	}

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
