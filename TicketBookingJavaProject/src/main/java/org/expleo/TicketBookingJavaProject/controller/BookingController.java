package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import org.expleo.TicketBookingJavaProject.service.MovieService;
import java.util.*;

public class BookingController {

	private BookingService service = new BookingService();
	private MovieService service1 = new MovieService();
	private Scanner sc = new Scanner(System.in);
	private SeatController seatController = new SeatController();

	public void startBookingFlow() {

		System.out.println("Select City:");
		List<City> cities = service.getCities();

		if (cities.isEmpty()) {
			System.out.println("No cities available. Admin must add data.");
			return;
		}

		service.displayCities(cities);
		int cityChoice = sc.nextInt();

		System.out.println("\nSelect Theatre:");
		List<Theatre> theatres = service.getTheatres();
		service.displayTheatres(theatres);
		int theatreChoice = sc.nextInt();

		System.out.println("\nSelect Movie:");
		List<Movie> movies = service.getMovies();
		service.displayMovies(movies);
		int movieChoice = sc.nextInt();

		System.out.println("\nSelect Showtime:");
		List<Showtime> shows = service.getShowtimes();
		service.displayShowtimes(shows);
		int showChoice = sc.nextInt();

		System.out.println("\nYou Selected:");
		System.out.println("City: " + cities.get(cityChoice - 1).getName());
		System.out.println("Theatre: " + theatres.get(theatreChoice - 1).getName());
		System.out.println("Movie: " + movies.get(movieChoice - 1).getTitle());
		System.out.println("Showtime: " + shows.get(showChoice - 1).getTime());
	}

	public void searchMovie() {

		System.out.println("1. Search by Title");
		System.out.println("2. Search by Language");

		int choice = sc.nextInt();
		sc.nextLine();

		if (choice == 1) {
			System.out.println("Enter title:");
			String title = sc.nextLine();

			List<Movie> result = service1.searchByTitle(title);
			service.displayMovies(result);

		} else if (choice == 2) {
			System.out.println("Enter language:");
			String lang = sc.nextLine();

			List<Movie> result = service1.searchByLanguage(lang);
			service.displayMovies(result);

		} else {
			System.out.println("Invalid choice");
		}
	}

	public void checkTicketAvailability(int ticketCount) {

		boolean isAvailable = service.validateTicketCount(ticketCount);

		if (!isAvailable) {
			System.out.println("Requested ticket count is invalid or not enough seats are available.");
			return;
		}

		System.out.println("Tickets available. Proceed to seat selection.");
		seatController.showSeatSelectionPage();

		List<Seat> selectedSeats = new ArrayList<>();

		if (ticketCount == 1) {
			Seat selectedSeat = null;
			while (selectedSeat == null) {
				selectedSeat = seatController.selectSingleSeat();
			}
			selectedSeats.add(selectedSeat);
		} else {
			while (selectedSeats.isEmpty()) {
				List<Seat> seats = seatController.selectMultipleSeats(ticketCount);
				if (seats != null) {
					selectedSeats = seats;
				}
			}
		}

		confirmBooking(ticketCount, selectedSeats);
	}

	public void confirmBooking(int ticketCount, List<Seat> selectedSeats) {
		System.out.println("\n========== BOOKING DETAILS ==========");
		System.out.println("Ticket Count: " + ticketCount);

		System.out.print("Selected Seats: ");
		for (Seat seat : selectedSeats) {
			System.out.print(seat.getSeatLabel() + " ");
		}
		System.out.println();

		System.out.println("Message        : Booking confirmed successfully.");
	}
}