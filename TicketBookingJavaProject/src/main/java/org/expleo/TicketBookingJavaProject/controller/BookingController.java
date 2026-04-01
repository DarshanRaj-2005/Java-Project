package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.service.SeatService;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import org.expleo.TicketBookingJavaProject.service.PaymentService;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/**
 * Controller for booking operations. Handles the complete booking flow from
 * movie selection to payment.
 */
public class BookingController {

	// Scanner for user input
	private Scanner sc = new Scanner(System.in);

	// Reference to SearchController (for pre-selected movie)
	private SearchController searchController;

	// Reference to MovieController
	private MovieController movieController;

	// Services for seat and booking operations
	private SeatService seatService = new SeatService();
	private BookingService bookingService = new BookingService();
	private PaymentService paymentService = new PaymentService();

	public BookingController(SearchController searchController, MovieController movieController) {
		this.searchController = searchController;
		this.movieController = movieController;
	}

	/**
	 * Starts the booking flow.
	 * 
	 * @param ticketCount Number of tickets to book (-1 if not specified)
	 */
	public void startBooking(int ticketCount) {
		System.out.println("\n--- BOOKING FLOW ---");

		Movie preSelected = searchController.getSelectedMovie();
		Theatre theatre = null;
		Movie movie = null;
		String city = null;

		if (preSelected != null) {
			// Movie was pre-selected through search
			System.out.println("Using previously selected movie: " + preSelected.getTitle() + " ("
					+ preSelected.getLanguage() + ")");

			// Step 1: Select City (get cities where this movie is available)
			city = selectCityForMovie(preSelected);
			if (city == null) {
				searchController.clearSelectedMovie();
				return;
			}

			// Step 2: Select Theatre in that city
			theatre = selectTheatreForMovie(preSelected, city);
			if (theatre == null) {
				searchController.clearSelectedMovie();
				return;
			}

			// Get the movie object from this theatre
			movie = movieController.getMoviesForTheatre(theatre.getId()).stream()
					.filter(m -> m.getTitle().equalsIgnoreCase(preSelected.getTitle())
							&& m.getLanguage().equalsIgnoreCase(preSelected.getLanguage()))
					.findFirst().orElse(null);

			if (movie == null) {
				System.out.println("Error: Movie not found in selected theatre!");
				searchController.clearSelectedMovie();
				return;
			}

		} else {
			// Normal flow - no pre-selected movie
			// Step 1: Select City
			city = selectCity();
			if (city == null)
				return;

			// Step 2: Select Theatre in that city
			theatre = selectTheatre(city);
			if (theatre == null)
				return;

			// Step 3: Select Movie
			movie = selectMovie(theatre.getId());
			if (movie == null)
				return;
		}

		// Step 4: Select Showtime
		String showtime = selectShowtime();
		if (showtime == null)
			return;

		// Step 5: Book Seats
		bookSeats(movie, theatre, city, showtime, ticketCount);

		// Clear selected movie after booking
		searchController.clearSelectedMovie();
	}

	/**
	 * Selects city when a movie is pre-selected. Only shows cities where the movie
	 * is available.
	 */
	private String selectCityForMovie(Movie movie) {
		// Get all theatres showing this movie
		List<Theatre> theatresWithMovie = searchController.getTheatresForSelectedMovie(null);

		if (theatresWithMovie.isEmpty()) {
			System.out.println("Error: This movie is not available in any theatre.");
			return null;
		}

		// Get unique cities
		Set<String> citiesSet = new HashSet<>();
		for (Theatre t : theatresWithMovie) {
			citiesSet.add(t.getCity());
		}

		List<String> cities = new ArrayList<>(citiesSet);
		Collections.sort(cities);

		System.out.println("\n--- SELECT CITY ---");
		System.out.println("Available cities where '" + movie.getTitle() + "' is playing:");

		for (int i = 0; i < cities.size(); i++) {
			System.out.println((i + 1) + ". " + cities.get(i));
		}

		System.out.print("Choice: ");
		int choice = InputUtil.getIntInput(sc);

		if (choice < 1 || choice > cities.size()) {
			System.out.println("Invalid selection!");
			return null;
		}

		return cities.get(choice - 1);
	}

	/**
	 * Selects theatre for a pre-selected movie in a specific city.
	 */
	private Theatre selectTheatreForMovie(Movie movie, String city) {
		List<Theatre> theatres = searchController.getTheatresForSelectedMovie(city);

		if (theatres.isEmpty()) {
			System.out.println("No theatres found in " + city + " for this movie.");
			return null;
		}

		System.out.println("\n--- SELECT THEATRE in " + city + " ---");
		System.out.println("Theatres showing '" + movie.getTitle() + "':");

		for (int i = 0; i < theatres.size(); i++) {
			Theatre t = theatres.get(i);
			System.out.println((i + 1) + ". " + t.getName());
		}

		System.out.print("Choice: ");
		int choice = InputUtil.getIntInput(sc);

		if (choice < 1 || choice > theatres.size()) {
			System.out.println("Invalid selection!");
			return null;
		}

		return theatres.get(choice - 1);
	}

	/**
	 * Selects city from all available cities.
	 */
	private String selectCity() {
		// Get cities from database
		List<String> cities = TheatreRepositoryImpl.getAllCities();

		if (cities.isEmpty()) {
			System.out.println("No cities available. Please contact Super Admin to add theatres.");
			return null;
		}

		System.out.println("\n--- SELECT CITY ---");
		for (int i = 0; i < cities.size(); i++) {
			System.out.println((i + 1) + ". " + cities.get(i));
		}

		System.out.print("Choice: ");
		int choice = InputUtil.getIntInput(sc);

		if (choice < 1 || choice > cities.size()) {
			System.out.println("Invalid selection!");
			return null;
		}

		return cities.get(choice - 1);
	}

	/**
	 * Selects theatre in a given city.
	 */
	private Theatre selectTheatre(String city) {
		List<Theatre> theatres = TheatreRepositoryImpl.getTheatresByCity(city);

		if (theatres.isEmpty()) {
			System.out.println("No theatres available in " + city + ".");
			System.out.println("Please contact Super Admin to add theatres.");
			return null;
		}

		System.out.println("\n--- SELECT THEATRE in " + city + " ---");
		for (int i = 0; i < theatres.size(); i++) {
			System.out.println((i + 1) + ". " + theatres.get(i).getName());
		}

		System.out.print("Choice: ");
		int choice = InputUtil.getIntInput(sc);

		if (choice < 1 || choice > theatres.size()) {
			System.out.println("Invalid selection!");
			return null;
		}

		return theatres.get(choice - 1);
	}

	/**
	 * Selects movie from a theatre.
	 */
	private Movie selectMovie(int theatreId) {
		List<Movie> movies = movieController.getMoviesForTheatre(theatreId);

		if (movies.isEmpty()) {
			System.out.println("No movies available in this theatre.");
			return null;
		}

		System.out.println("\n--- SELECT MOVIE ---");
		for (int i = 0; i < movies.size(); i++) {
			Movie m = movies.get(i);
			System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | "
					+ m.getDuration() + " mins");
		}

		System.out.print("Choice: ");
		int choice = InputUtil.getIntInput(sc);

		if (choice < 1 || choice > movies.size()) {
			System.out.println("Invalid selection!");
			return null;
		}

		return movies.get(choice - 1);
	}

	/**
	 * Selects showtime for the movie.
	 */
	private String selectShowtime() {
		List<String> shows = Arrays.asList("10:00 AM", "01:30 PM", "06:00 PM", "10:00 PM");

		System.out.println("\n--- SELECT SHOWTIME ---");
		for (int i = 0; i < shows.size(); i++) {
			System.out.println((i + 1) + ". " + shows.get(i));
		}

		System.out.print("Choice: ");
		int choice = InputUtil.getIntInput(sc);

		if (choice < 1 || choice > shows.size()) {
			System.out.println("Invalid selection!");
			return null;
		}

		return shows.get(choice - 1);
	}

	/**
	 * Handles seat selection and booking process.
	 */
	private void bookSeats(Movie movie, Theatre theatre, String city, String showtime, int ticketCount) {
		String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime.replace(" ", "_").replace(":", "");

		// Get number of tickets
		if (ticketCount <= 0) {
			System.out.print("\nEnter number of tickets to book: ");
			ticketCount = InputUtil.getIntInput(sc);

			if (ticketCount <= 0) {
				System.out.println("Error: Please enter a valid number of tickets!");
				return;
			}
		}

		// Display seat layout
		displaySeatLayout(sessionKey);

		// Get seat selection from user
		List<String> selectedSeats = new ArrayList<>();

		System.out.println("Enter " + ticketCount + " seat labels to book (comma-separated, e.g., A1, A2): ");
		String input = sc.nextLine().toUpperCase();

		// Parse seat labels (comma or space separated)
		String[] labels = input.split("[,\\s]+");
		for (String label : labels) {
			if (!label.trim().isEmpty()) {
				selectedSeats.add(label.trim());
			}
		}

		// Validate seat count
		if (selectedSeats.size() != ticketCount) {
			System.out.println("Error: You must select exactly " + ticketCount + " seats!");
			return;
		}

		if (selectedSeats.isEmpty()) {
			System.out.println("Error: No seats selected!");
			return;
		}

		// Validate seat selection
		String validation = seatService.validateMultipleSeatSelection(sessionKey, selectedSeats, ticketCount);
		if (!validation.equals("VALID")) {
			System.out.println("Error: " + validation);
			return;
		}

		// Calculate total price
		double seatPrice = 200.0;
		double totalAmount = ticketCount * seatPrice;

		// Show booking summary
		System.out.println("\n--- BOOKING SUMMARY ---");
		System.out.println("Movie: " + movie.getTitle() + " (" + movie.getLanguage() + ")");
		System.out.println("Theatre: " + theatre.getName() + " (" + city + ")");
		System.out.println("Showtime: " + showtime);
		System.out.println("Seats: " + String.join(", ", selectedSeats));
		System.out.println("Total Amount to Pay: Rs. " + totalAmount);

		// Proceed to payment
		System.out.print("\nProceed to Payment? (yes/no): ");
		if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
			System.out.println("Booking cancelled.");
			return;
		}

		// Process payment
		processPayment(movie, theatre, city, showtime, selectedSeats, totalAmount);
	}

	/**
	 * Processes payment for the booking.
	 */
	private void processPayment(Movie movie, Theatre theatre, String city, String showtime, List<String> selectedSeats,
			double totalAmount) {
		System.out.print("Enter Payment Method (Card/UPI/Cash): ");
		String method = sc.nextLine().trim();

		try {
			if (method.equalsIgnoreCase("Card")) {
				System.out.print("Enter Card Number (16 digits): ");
				String cardNumber = sc.nextLine().trim();
				System.out.print("Enter CVV (3 digits): ");
				String cvv = sc.nextLine().trim();
				paymentService.validateCardPayment(cardNumber, cvv);

			} else if (method.equalsIgnoreCase("UPI")) {
				System.out.print("Enter UPI ID (e.g., user@bank): ");
				String upiId = sc.nextLine().trim();
				paymentService.validateUpiPayment(upiId);

			} else if (!method.equalsIgnoreCase("Cash")) {
				System.out.println("Error: Invalid payment method selected.");
				return;
			}
		} catch (org.expleo.TicketBookingJavaProject.exception.PaymentErrorException e) {
			System.out.println("Payment Failed: " + e.getMessage());
			return;
		}

		System.out.println("Payment Successful via " + method + "!");

		// Create booking
		String bookingId = bookingService.generateBookingId();
		Booking booking = new Booking(bookingId, movie.getId(), theatre.getId(), showtime, selectedSeats, totalAmount,
				"CONFIRMED");
		bookingService.confirmBooking(booking);

		// Update seat status
		String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime.replace(" ", "_").replace(":", "");
		for (String label : selectedSeats) {
			Seat s = seatService.getSeatByLabel(sessionKey, label);
			if (s != null) {
				s.setStatus("BOOKED");
				seatService.updateSeat(s);
			}
		}

		// Display confirmation
		System.out.println("\n=================================");
		System.out.println("       BOOKING CONFIRMED!        ");
		System.out.println("=================================");
		System.out.println("Booking ID: " + bookingId);
		System.out.println("Movie: " + movie.getTitle() + " (" + movie.getLanguage() + ")");
		System.out.println("Theatre: " + theatre.getName() + " (" + city + ")");
		System.out.println("Showtime: " + showtime);
		System.out.println("Seats: " + String.join(", ", selectedSeats));
		System.out.println("Total Amount PAID: Rs. " + totalAmount);
		System.out.println("=================================");
	}

	/**
	 * Displays the seat layout for a session.
	 */
	private void displaySeatLayout(String sessionKey) {
		System.out.println("\n--- SEAT LAYOUT ---");
		List<Seat> seats = seatService.getSeatLayout(sessionKey);

		char currentRow = ' ';
		for (Seat s : seats) {
			if (s.getRow().charAt(0) != currentRow) {
				if (currentRow != ' ')
					System.out.println();
				currentRow = s.getRow().charAt(0);
				System.out.print(currentRow + " | ");
			}
			String statusSym = s.getStatus().equalsIgnoreCase("AVAILABLE") ? "[ ]" : "[X]";
			System.out.print(s.getSeatLabel() + statusSym + " ");
		}
		System.out.println("\n[ ] = Available  [X] = Booked");
	}

	/**
	 * Cancels an existing booking.
	 */
	public void cancelBooking() {
		System.out.println("\n--- CANCEL BOOKING ---");
		System.out.print("Enter Booking ID: ");
		String id = sc.nextLine().toUpperCase().trim();

		try {
			bookingService.cancelBooking(id);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
