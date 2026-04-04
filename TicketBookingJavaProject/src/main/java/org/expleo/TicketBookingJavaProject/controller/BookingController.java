package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.service.SeatService;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import org.expleo.TicketBookingJavaProject.service.PaymentService;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/**
 * Controller for booking operations. Handles the complete booking flow from
 * movie selection to payment with tiered pricing, GST, and application fee.
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

    // GST percentage applied to ticket amount
    private static final double GST_PERCENTAGE = 3.5;
    
    // Application fee per ticket
    private static final double APPLICATION_FEE = 10.0;

    public BookingController(SearchController searchController, MovieController movieController) {
        this.searchController = searchController;
        this.movieController = movieController;
    }

    /**
     * Starts the booking flow for a specific theatre (used by Officers).
     * Officer can only book tickets for their assigned theatre.
     * 
     * @param theatre The theatre to book tickets for
     * @param officerUserId User ID of the officer (for tracking)
     */
    public void startBookingForTheatre(Theatre theatre, int officerUserId) {
        // Select movie from this theatre only
        Movie movie = selectMovieFromTheatre(theatre);
        if (movie == null) {
            return;
        }

        // Select showtime
        String showtime = selectShowtime();
        if (showtime == null) {
            return;
        }

        // Book seats
        bookSeats(movie, theatre, theatre.getCity(), showtime, -1, officerUserId);
    }

    /**
     * Selects a movie from a specific theatre.
     */
    private Movie selectMovieFromTheatre(Theatre theatre) {
        List<Movie> movies = movieController.getMoviesForTheatre(theatre.getId());

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
     * Starts the booking flow.
     * 
     * @param ticketCount Number of tickets to book (-1 if not specified)
     * @param userId User ID of the customer (0 for guest)
     */
    public void startBooking(int ticketCount, int userId) {
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
        bookSeats(movie, theatre, city, showtime, ticketCount, userId);

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
     * Handles seat selection and booking process with tiered pricing.
     */
    private void bookSeats(Movie movie, Theatre theatre, String city, String showtime, int ticketCount, int userId) {
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

        // Display seat layout with prices
        displaySeatLayoutWithPrices(sessionKey);

        // Get seat selection from user
        List<String> selectedSeats = selectSeats(sessionKey, ticketCount);
        if (selectedSeats == null) {
            return;
        }

        // Calculate total price based on seat rows
        double[] priceInfo = calculatePrice(selectedSeats);
        double ticketAmount = priceInfo[0];
        
        // Create bill details
        BillDetails bill = new BillDetails(ticketCount, ticketAmount, GST_PERCENTAGE, APPLICATION_FEE);

        // Show booking summary
        showBookingSummary(movie, theatre, city, showtime, selectedSeats, bill);

        // Ask user if they want to modify seats
        List<String> finalSeats = selectedSeats;
        while (true) {
            System.out.print("\nDo you want to modify seats? (yes/no): ");
            String modifyChoice = sc.nextLine().trim().toLowerCase();
            
            if (modifyChoice.equals("yes")) {
                // Release current seats first
                for (String label : finalSeats) {
                    Seat s = seatService.getSeatByLabel(sessionKey, label);
                    if (s != null) {
                        s.setStatus("AVAILABLE");
                        seatService.updateSeat(s);
                    }
                }
                
                // Get new number of tickets
                System.out.print("Enter new number of tickets: ");
                ticketCount = InputUtil.getIntInput(sc);
                
                if (ticketCount <= 0) {
                    System.out.println("Error: Please enter a valid number of tickets!");
                    return;
                }
                
                // Display updated seat layout
                displaySeatLayoutWithPrices(sessionKey);
                
                // Select new seats
                finalSeats = selectSeats(sessionKey, ticketCount);
                if (finalSeats == null) {
                    return;
                }
                
                // Recalculate price
                priceInfo = calculatePrice(finalSeats);
                ticketAmount = priceInfo[0];
                bill = new BillDetails(ticketCount, ticketAmount, GST_PERCENTAGE, APPLICATION_FEE);
                
                // Show updated summary
                showBookingSummary(movie, theatre, city, showtime, finalSeats, bill);
            } else if (modifyChoice.equals("no")) {
                break;
            } else {
                System.out.println("Invalid choice! Please enter 'yes' or 'no'.");
            }
        }

        // Proceed to payment
        System.out.print("\nProceed to Payment? (yes/no): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
            System.out.println("Booking cancelled.");
            // Release seats
            for (String label : finalSeats) {
                Seat s = seatService.getSeatByLabel(sessionKey, label);
                if (s != null) {
                    s.setStatus("AVAILABLE");
                    seatService.updateSeat(s);
                }
            }
            return;
        }

        // Process payment
        processPayment(movie, theatre, city, showtime, finalSeats, bill, userId);
    }

    /**
     * Selects seats from user input.
     */
    private List<String> selectSeats(String sessionKey, int ticketCount) {
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
            return null;
        }

        if (selectedSeats.isEmpty()) {
            System.out.println("Error: No seats selected!");
            return null;
        }

        // Validate seat selection
        String validation = seatService.validateMultipleSeatSelection(sessionKey, selectedSeats, ticketCount);
        if (!validation.equals("VALID")) {
            System.out.println("Error: " + validation);
            return null;
        }

        return selectedSeats;
    }

    /**
     * Calculates price based on seat rows.
     * Top 3 rows (A, B, C): Rs. 190
     * Next rows (D, E, F, G): Rs. 160
     * Bottom rows (H, I, J): Rs. 60
     */
    private double[] calculatePrice(List<String> seats) {
        double totalPrice = 0;
        StringBuilder priceBreakdown = new StringBuilder();
        
        for (String seat : seats) {
            double price = 0;
            if (seat.length() > 0) {
                char row = seat.charAt(0);
                
                // Top 3 rows: A, B, C -> Rs. 190
                if (row >= 'A' && row <= 'C') {
                    price = 190;
                }
                // Next rows: D, E, F, G -> Rs. 160
                else if (row >= 'D' && row <= 'G') {
                    price = 160;
                }
                // Bottom rows: H, I, J -> Rs. 60
                else if (row >= 'H' && row <= 'J') {
                    price = 60;
                }
                // Default price if row doesn't match
                else {
                    price = 160;
                }
            }
            
            totalPrice += price;
            if (!priceBreakdown.isEmpty()) {
                priceBreakdown.append(", ");
            }
            priceBreakdown.append(seat).append(": Rs.").append((int)price);
        }
        
        System.out.println("\nSeat Prices: " + priceBreakdown.toString());
        return new double[]{totalPrice, GST_PERCENTAGE, APPLICATION_FEE};
    }

    /**
     * Displays the seat layout with prices for each row.
     */
    private void displaySeatLayoutWithPrices(String sessionKey) {
        System.out.println("\n--- SEAT LAYOUT WITH PRICES ---");
        System.out.println("Row A-C: Rs.190 | Row D-G: Rs.160 | Row H-J: Rs.60");
        System.out.println("[ ] = Available  [X] = Booked");
        System.out.println();
        
        List<Seat> seats = seatService.getSeatLayout(sessionKey);

        char currentRow = ' ';
        for (Seat s : seats) {
            if (s.getRow().charAt(0) != currentRow) {
                if (currentRow != ' ') {
                    System.out.println();
                }
                currentRow = s.getRow().charAt(0);
                
                // Show row price
                String rowPrice = "";
                if (currentRow >= 'A' && currentRow <= 'C') {
                    rowPrice = " (Rs.190)";
                } else if (currentRow >= 'D' && currentRow <= 'G') {
                    rowPrice = " (Rs.160)";
                } else {
                    rowPrice = " (Rs.60)";
                }
                System.out.print(currentRow + rowPrice + " | ");
            }
            String statusSym = s.getStatus().equalsIgnoreCase("AVAILABLE") ? "[ ]" : "[X]";
            System.out.print(s.getSeatLabel() + statusSym + " ");
        }
        System.out.println();
    }

    /**
     * Shows the booking summary with bill details.
     */
    private void showBookingSummary(Movie movie, Theatre theatre, String city, String showtime, 
                                    List<String> selectedSeats, BillDetails bill) {
        System.out.println("\n--- BOOKING SUMMARY ---");
        System.out.println("Movie: " + movie.getTitle() + " (" + movie.getLanguage() + ")");
        System.out.println("Theatre: " + theatre.getName() + " (" + city + ")");
        System.out.println("Showtime: " + showtime);
        System.out.println("Seats: " + String.join(", ", selectedSeats));
        bill.printBill();
    }

    /**
     * Processes payment for the booking.
     */
    private void processPayment(Movie movie, Theatre theatre, String city, String showtime, 
                                List<String> selectedSeats, BillDetails bill, int userId) {
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
        Booking booking = new Booking(bookingId, userId, movie.getId(), theatre.getId(), showtime, 
                                       selectedSeats, bill.getTotalAmount(), "CONFIRMED");
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
        System.out.println("-------------------------------------------");
        bill.printBill();
        System.out.println("=================================");
        System.out.println("Payment Method: " + method);
        System.out.println("=================================");
    }

    /**
     * Displays the seat layout for a session (legacy method).
     */
    private void displaySeatLayout(String sessionKey) {
        displaySeatLayoutWithPrices(sessionKey);
    }

    /**
     * Cancels an existing booking.
     */
    public void cancelBooking(int userId) {
        System.out.println("\n--- CANCEL BOOKING ---");
        
        // First show user's bookings if they are logged in
        if (userId > 0) {
            List<Booking> userBookings = BookingRepositoryImpl.getBookingsByUserId(userId);
            if (!userBookings.isEmpty()) {
                System.out.println("\nYour Bookings:");
                for (Booking b : userBookings) {
                    if (b.getStatus().equalsIgnoreCase("CONFIRMED")) {
                        System.out.println("- " + b.getBookingId() + " | Seats: " + String.join(",", b.getSeatLabels()) 
                            + " | Amount: Rs." + b.getTotalAmount());
                    }
                }
            }
        }
        
        System.out.print("Enter Booking ID: ");
        String id = sc.nextLine().toUpperCase().trim();

        try {
            Booking booking = BookingRepositoryImpl.getBookingById(id);
            if (booking != null) {
                // Check if user owns this booking (for customers)
                if (userId > 0 && booking.getUserId() != userId) {
                    System.out.println("Error: You can only cancel your own bookings!");
                    return;
                }
                
                double refundAmount = booking.getTotalAmount();
                bookingService.cancelBooking(id);
                
                // Show refund message
                System.out.println("\n=================================");
                System.out.println("      REFUND INFORMATION          ");
                System.out.println("=================================");
                System.out.println("Booking ID: " + id);
                System.out.println("Amount Paid: Rs." + refundAmount);
                System.out.println("Refund Amount: Rs." + refundAmount);
                System.out.println("-------------------------------------------");
                System.out.println("NOTE: Refund will be processed within 5-7 business days.");
                System.out.println("=================================");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Views bookings for a customer.
     */
    public void viewMyBookings(int userId) {
        System.out.println("\n--- MY BOOKINGS ---");
        
        if (userId <= 0) {
            System.out.println("Please login to view your bookings.");
            return;
        }
        
        List<Booking> userBookings = BookingRepositoryImpl.getBookingsByUserId(userId);
        
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings yet.");
            return;
        }
        
        System.out.println("\n+------------------------------------------+");
        System.out.println("|           YOUR BOOKINGS                   |");
        System.out.println("+------------------------------------------+");
        
        for (Booking b : userBookings) {
            // Get movie and theatre details
            Movie movie = MovieRepositoryImpl.getMovieById(b.getMovieId());
            Theatre theatre = TheatreRepositoryImpl.getTheatreById(b.getTheatreId());
            
            String movieName = (movie != null) ? movie.getTitle() : "Unknown";
            String theatreName = (theatre != null) ? theatre.getName() : "Unknown";
            
            System.out.println("| Booking ID: " + b.getBookingId());
            System.out.println("| Movie: " + movieName);
            System.out.println("| Theatre: " + theatreName);
            System.out.println("| Showtime: " + b.getShowtime());
            System.out.println("| Seats: " + String.join(", ", b.getSeatLabels()));
            System.out.println("| Amount: Rs." + b.getTotalAmount());
            System.out.println("| Status: " + b.getStatus());
            System.out.println("+------------------------------------------+");
        }
    }
}
