package org.expleo.TicketBookingJavaProject.controller;
import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import java.util.*;

public class BookingController {

	private BookingService service = new BookingService();
	private Scanner sc = new Scanner(System.in);

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

	private SeatController seatController = new SeatController();

	public void checkTicketAvailability(int ticketCount) {

		boolean isAvailable = service.validateTicketCount(ticketCount);

		if (isAvailable) {
			System.out.println("Tickets available. Proceed to seat selection.");
			seatController.showSeatSelectionPage();
		} else {
			System.out.println("Not enough seats available.");
		}
	}
}
