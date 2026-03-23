package org.expleo.TicketBookingJavaProject.controller;

import java.util.List;
import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.City;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Showtime;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.service.SelectionService;


public class SelectionController {
	
	private Scanner sc = new Scanner(System.in);
	SelectionService service = new SelectionService();
	
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
}
