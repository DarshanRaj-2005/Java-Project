/*
 * FILE: BookingController.java
 * PURPOSE: Handles the complete ticket booking process.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields and methods
 * - Abstraction: Hides complex booking logic
 * - Composition: Uses SeatService, BookingService, PaymentService
 * - Inheritance: Extends BaseController
 * - Polymorphism: Overrides showMenu() method
 * 
 * WHAT THIS FILE DOES:
 * - Shows movie, theatre, and seat selection
 * - Calculates ticket prices with GST
 * - Processes payments
 * - Confirms or cancels bookings
 * 
 * BOOKING FLOW:
 * 1. Select movie
 * 2. Select city
 * 3. Select theatre
 * 4. Select showtime
 * 5. Select seats
 * 6. Review and pay
 * 7. Confirmation
 */



//------------Author Name: Darshan Raj, Rohini, Tamil Kumar, Krishna Prasath---------------



package org.expleo.TicketBookingJavaProject.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.expleo.TicketBookingJavaProject.exception.BookingNotFoundException;
import org.expleo.TicketBookingJavaProject.model.BillDetails;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import org.expleo.TicketBookingJavaProject.service.PaymentService;
import org.expleo.TicketBookingJavaProject.service.SeatService;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

public class BookingController extends BaseController {

    private TheatreRepositoryImpl theatreDAO = new TheatreRepositoryImpl();
    private MovieRepositoryImpl movieDAO = new MovieRepositoryImpl();
    private BookingRepositoryImpl bookingDAO = new BookingRepositoryImpl();
    private UserRepositoryImpl userDAO = UserRepositoryImpl.getInstance();

    private SearchController searchController;
    private MovieController movieController;
    private SeatService seatService = new SeatService();
    private BookingService bookingService = new BookingService();
    private PaymentService paymentService = new PaymentService();

    private static final double GST_PERCENTAGE = 3.5;
    private static final double APPLICATION_FEE = 10.0;

    public BookingController(SearchController searchController, MovieController movieController) {
        super();
        this.searchController = searchController;
        this.movieController = movieController;
        printInfo("BookingController initialized");
    }

    public BookingController(SearchController searchController, MovieController movieController, Scanner sharedScanner) {
        super(sharedScanner);
        this.searchController = searchController;
        this.movieController = movieController;
        printInfo("BookingController initialized");
    }

    @Override
    public void showMenu() {
        printHeader("BOOKING");
        System.out.println("1. Book Ticket");
        System.out.println("2. Cancel Booking");
        System.out.println("3. View My Bookings");
        System.out.println("4. Back");
        
        int choice = getValidChoice(1, 4);
        
        switch (choice) {
            case 1:
                printInfo("Please provide user ID and ticket count for booking.");
                break;
            case 2:
                printInfo("Please provide user ID for cancellation.");
                break;
            case 3:
                printInfo("Please provide user ID to view bookings.");
                break;
            case 4:
                stop();
                break;
            default:
                printError("Invalid option");
        }
    }

    public void startBookingForTheatre(Theatre theatre, int officerUserId) {
        Movie movie = selectMovieFromTheatre(theatre);
        if (movie == null) {
            return;
        }

        String showtime = selectShowtime();
        if (showtime == null) {
            return;
        }

        bookSeats(movie, theatre, theatre.getCity(), showtime, -1, officerUserId);
    }

    private Movie selectMovieFromTheatre(Theatre theatre) {
        List<Movie> movies = movieController.getMoviesForTheatre(theatre.getId());

        if (movies.isEmpty()) {
            printInfo("No movies available in this theatre.");
            return null;
        }

        printSubHeader("SELECT MOVIE");
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > movies.size()) {
            printError("Invalid selection!");
            return null;
        }

        return movies.get(choice - 1);
    }

    public void startBooking(int ticketCount, int userId) {
        printSubHeader("BOOKING FLOW");

        Movie preSelected = searchController.getSelectedMovie();
        Theatre theatre = null;
        Movie movie = null;
        String city = null;

        if (preSelected != null) {
            printInfo("Using previously selected movie: " + preSelected.getTitle() + " (" + preSelected.getLanguage() + ")");

            city = selectCityForMovie(preSelected);
            if (city == null) {
                searchController.clearSelectedMovie();
                return;
            }

            theatre = selectTheatreForMovie(preSelected, city);
            if (theatre == null) {
                searchController.clearSelectedMovie();
                return;
            }

            movie = movieController.getMoviesForTheatre(theatre.getId()).stream()
                    .filter(m -> m.getTitle().equalsIgnoreCase(preSelected.getTitle())
                            && m.getLanguage().equalsIgnoreCase(preSelected.getLanguage()))
                    .findFirst().orElse(null);

            if (movie == null) {
                printError("Movie not found in selected theatre!");
                searchController.clearSelectedMovie();
                return;
            }

        } else {
            city = selectCity();
            if (city == null) return;

            theatre = selectTheatre(city);
            if (theatre == null) return;

            movie = selectMovie(theatre.getId());
            if (movie == null) return;
        }

        String showtime = selectShowtime();
        if (showtime == null) return;

        bookSeats(movie, theatre, city, showtime, ticketCount, userId);
        searchController.clearSelectedMovie();
    }

    private String selectCityForMovie(Movie movie) {
        List<Theatre> theatresWithMovie = searchController.getTheatresForSelectedMovie(null);

        if (theatresWithMovie.isEmpty()) {
            printError("This movie is not available in any theatre.");
            return null;
        }

        Set<String> citiesSet = new HashSet<>();
        for (Theatre t : theatresWithMovie) {
            citiesSet.add(t.getCity());
        }

        List<String> cities = new ArrayList<>(citiesSet);
        Collections.sort(cities);

        printSubHeader("SELECT CITY");
        System.out.println("Available cities where '" + movie.getTitle() + "' is playing:");

        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > cities.size()) {
            printError("Invalid selection!");
            return null;
        }

        return cities.get(choice - 1);
    }

    private Theatre selectTheatreForMovie(Movie movie, String city) {
        List<Theatre> theatres = searchController.getTheatresForSelectedMovie(city);

        if (theatres.isEmpty()) {
            printInfo("No theatres found in " + city + " for this movie.");
            return null;
        }

        printSubHeader("SELECT THEATRE in " + city);
        System.out.println("Theatres showing '" + movie.getTitle() + "':");

        for (int i = 0; i < theatres.size(); i++) {
            Theatre t = theatres.get(i);
            System.out.println((i + 1) + ". " + t.getName());
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > theatres.size()) {
            printError("Invalid selection!");
            return null;
        }

        return theatres.get(choice - 1);
    }

    private String selectCity() {
        List<String> cities = theatreDAO.getAllCities();

        if (cities.isEmpty()) {
            printInfo("No cities available. Please contact Super Admin to add theatres.");
            return null;
        }

        printSubHeader("SELECT CITY");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > cities.size()) {
            printError("Invalid selection!");
            return null;
        }

        return cities.get(choice - 1);
    }

    private Theatre selectTheatre(String city) {
        List<Theatre> theatres = theatreDAO.getTheatresByCity(city);

        if (theatres.isEmpty()) {
            printInfo("No theatres available in " + city + ".");
            return null;
        }

        printSubHeader("SELECT THEATRE in " + city);
        for (int i = 0; i < theatres.size(); i++) {
            System.out.println((i + 1) + ". " + theatres.get(i).getName());
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > theatres.size()) {
            printError("Invalid selection!");
            return null;
        }

        return theatres.get(choice - 1);
    }

    private Movie selectMovie(int theatreId) {
        List<Movie> movies = movieController.getMoviesForTheatre(theatreId);

        if (movies.isEmpty()) {
            printInfo("No movies available in this theatre.");
            return null;
        }

        printSubHeader("SELECT MOVIE");
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > movies.size()) {
            printError("Invalid selection!");
            return null;
        }

        return movies.get(choice - 1);
    }

    private String selectShowtime() {
        List<String> shows = Arrays.asList("10:00 AM", "01:30 PM", "06:00 PM", "10:00 PM");

        printSubHeader("SELECT SHOWTIME");
        for (int i = 0; i < shows.size(); i++) {
            System.out.println((i + 1) + ". " + shows.get(i));
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > shows.size()) {
            printError("Invalid selection!");
            return null;
        }

        return shows.get(choice - 1);
    }

    private void bookSeats(Movie movie, Theatre theatre, String city, String showtime, int ticketCount, int userId) {
        String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime.replace(" ", "_").replace(":", "");

        if (ticketCount <= 0) {
            System.out.print("\nEnter number of tickets to book: ");
            ticketCount = InputUtil.getIntInput(sc);

            if (ticketCount <= 0) {
                printError("Please enter a valid number of tickets!");
                return;
            }
        }

        if (ticketCount > 10) {
            printError("You can only book up to 10 seats in a single transaction!");
            return;
        }

        List<Seat> allSeats = seatService.getSeatLayout(sessionKey);
        List<Seat> availableSeats = seatService.getAvailableSeats(sessionKey);
        int totalCapacity = allSeats.size();
        int availableCount = availableSeats.size();

        if (ticketCount > totalCapacity) {
            printError("Requested seats exceed theatre capacity!");
            return;
        }

        if (ticketCount > availableCount) {
            printError("Only " + availableCount + " seats are available.");
            return;
        }

        displaySeatLayoutWithPrices(sessionKey);

        List<String> selectedSeats = selectSeats(sessionKey, ticketCount);
        if (selectedSeats == null) {
            return;
        }

        double[] priceInfo = calculatePrice(selectedSeats);
        double ticketAmount = priceInfo[0];
        BillDetails bill = new BillDetails(ticketCount, ticketAmount, GST_PERCENTAGE, APPLICATION_FEE);

        showBookingSummary(movie, theatre, city, showtime, selectedSeats, bill);

        List<String> finalSeats = selectedSeats;
        while (true) {
            System.out.print("\nDo you want to modify seats? (yes/no): ");
            String modifyChoice = sc.nextLine().trim().toLowerCase();
            
            if (modifyChoice.equals("yes")) {
                for (String label : finalSeats) {
                    Seat s = seatService.getSeatByLabel(sessionKey, label);
                    if (s != null) {
                        s.setStatus("AVAILABLE");
                        seatService.updateSeat(s);
                    }
                }
                
                System.out.print("Enter new number of tickets: ");
                ticketCount = InputUtil.getIntInput(sc);
                
                if (ticketCount <= 0) {
                    printError("Please enter a valid number of tickets!");
                    return;
                }

                if (ticketCount > 10) {
                    printError("Maximum 10 seats per transaction!");
                    return;
                }

                List<Seat> currentAvailable = seatService.getAvailableSeats(sessionKey);
                int currentTotal = seatService.getSeatLayout(sessionKey).size();
                
                if (ticketCount > currentTotal) {
                    printError("Requested seats exceed theatre capacity!");
                    return;
                }
                
                if (ticketCount > currentAvailable.size()) {
                    printError("Only " + currentAvailable.size() + " seats are available.");
                    return;
                }
                
                displaySeatLayoutWithPrices(sessionKey);
                
                finalSeats = selectSeats(sessionKey, ticketCount);
                if (finalSeats == null) {
                    return;
                }
                
                priceInfo = calculatePrice(finalSeats);
                ticketAmount = priceInfo[0];
                bill = new BillDetails(ticketCount, ticketAmount, GST_PERCENTAGE, APPLICATION_FEE);
                
                showBookingSummary(movie, theatre, city, showtime, finalSeats, bill);
            } else if (modifyChoice.equals("no")) {
                break;
            } else {
                printError("Invalid choice! Please enter 'yes' or 'no'.");
            }
        }

        System.out.print("\nProceed to Payment? (yes/no): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
            printInfo("Booking cancelled.");
            for (String label : finalSeats) {
                Seat s = seatService.getSeatByLabel(sessionKey, label);
                if (s != null) {
                    s.setStatus("AVAILABLE");
                    seatService.updateSeat(s);
                }
            }
            return;
        }

        processPayment(movie, theatre, city, showtime, finalSeats, bill, userId);
    }

    private List<String> selectSeats(String sessionKey, int ticketCount) {
        List<String> selectedSeats = new ArrayList<>();

        System.out.println("Enter " + ticketCount + " seat labels to book (comma-separated, e.g., A1, A2): ");
        String input = sc.nextLine().toUpperCase();

        String[] labels = input.split("[,\\s]+");
        for (String label : labels) {
            if (!label.trim().isEmpty()) {
                selectedSeats.add(label.trim());
            }
        }

        if (selectedSeats.size() != ticketCount) {
            printError("You must select exactly " + ticketCount + " seats!");
            return null;
        }

        if (selectedSeats.isEmpty()) {
            printError("No seats selected!");
            return null;
        }

        String validation = seatService.validateMultipleSeatSelection(sessionKey, selectedSeats, ticketCount);
        if (!validation.equals("VALID")) {
            printError(validation);
            return null;
        }

        return selectedSeats;
    }

    private double[] calculatePrice(List<String> seats) {
        double totalPrice = 0;
        StringBuilder priceBreakdown = new StringBuilder();
        
        for (String seat : seats) {
            double price = 160;
            if (seat.length() > 0) {
                char row = seat.charAt(0);
                if (row >= 'A' && row <= 'C') {
                    price = 190;
                } else if (row >= 'D' && row <= 'G') {
                    price = 160;
                } else if (row >= 'H' && row <= 'J') {
                    price = 60;
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

    private void displaySeatLayoutWithPrices(String sessionKey) {
        printSubHeader("SEAT LAYOUT WITH PRICES");
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

    private void showBookingSummary(Movie movie, Theatre theatre, String city, String showtime, 
                                    List<String> selectedSeats, BillDetails bill) {
        printSubHeader("BOOKING SUMMARY");
        System.out.println("Movie: " + movie.getTitle() + " (" + movie.getLanguage() + ")");
        System.out.println("Theatre: " + theatre.getName() + " (" + city + ")");
        System.out.println("Showtime: " + showtime);
        System.out.println("Seats: " + String.join(", ", selectedSeats));
        bill.printBill();
    }

    private void processPayment(Movie movie, Theatre theatre, String city, String showtime, 
                                List<String> selectedSeats, BillDetails bill, int userId) {
        String method = "";
        boolean validPayment = false;

        String role = "Customer";
        if (userId > 0) {
            User user = userDAO.getUserById(userId);
            if (user != null) {
                role = user.getRole();
            }
        }

        while (!validPayment) {
            System.out.print("\nEnter Payment Method (Card/UPI/Cash): ");
            method = sc.nextLine().trim();

            try {
                if (method.equalsIgnoreCase("Card")) {
                    System.out.print("Enter Card Number (16 digits): ");
                    String cardNumber = sc.nextLine().trim();
                    System.out.print("Enter CVV (3 digits): ");
                    String cvv = sc.nextLine().trim();
                    paymentService.validateCardPayment(cardNumber, cvv);
                    validPayment = true;

                } else if (method.equalsIgnoreCase("UPI")) {
                    System.out.print("Enter UPI ID (e.g., user@bank): ");
                    String upiId = sc.nextLine().trim();
                    paymentService.validateUpiPayment(upiId);
                    validPayment = true;

                } else if (method.equalsIgnoreCase("Cash")) {
                    if (role.equalsIgnoreCase("Customer") || userId <= 0) {
                        printError("Cash not allowed for online booking!");
                    } else {
                        validPayment = true;
                    }
                } else {
                    printError("Invalid payment method!");
                }
            } catch (org.expleo.TicketBookingJavaProject.exception.PaymentErrorException e) {
                printError(e.getMessage());
                System.out.print("Try another payment? (yes/no): ");
                if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                    printInfo("Booking cancelled due to payment failure.");
                    String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime.replace(" ", "_").replace(":", "");
                    for (String label : selectedSeats) {
                        Seat s = seatService.getSeatByLabel(sessionKey, label);
                        if (s != null) {
                            s.setStatus("AVAILABLE");
                            seatService.updateSeat(s);
                        }
                    }
                    return;
                }
            }
        }

        printSuccess("Payment Successful via " + method + "!");

        String bookingId = bookingService.generateBookingId();
        Booking booking = new Booking(bookingId, userId, movie.getId(), theatre.getId(), showtime, 
                                       selectedSeats, bill.getTotalAmount(), "CONFIRMED");
        bookingService.confirmBooking(booking);

        String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime.replace(" ", "_").replace(":", "");
        for (String label : selectedSeats) {
            Seat s = seatService.getSeatByLabel(sessionKey, label);
            if (s != null) {
                s.setStatus("BOOKED");
                seatService.updateSeat(s);
            }
        }

        printHeader("BOOKING CONFIRMED!");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Movie: " + movie.getTitle() + " (" + movie.getLanguage() + ")");
        System.out.println("Theatre: " + theatre.getName() + " (" + city + ")");
        System.out.println("Showtime: " + showtime);
        System.out.println("Seats: " + String.join(", ", selectedSeats));
        printSeparator();
        bill.printBill();
        printSeparator();
        System.out.println("Payment Method: " + method);
        printHeader("END");
    }

    public void cancelBooking(int userId) {
        printSubHeader("CANCEL BOOKING");
        
        if (userId > 0) {
            List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId);
            if (!userBookings.isEmpty()) {
                printInfo("Your Bookings:");
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
            Booking booking = bookingDAO.getBookingById(id);

            if (booking == null) {
                throw new BookingNotFoundException("Booking with ID " + id + " not found!");
            }

            if (userId > 0 && booking.getUserId() != userId) {
                printError("You can only cancel your own bookings!");
                return;
            }

            double refundAmount = booking.getTotalAmount();
            bookingService.cancelBooking(id);

            printHeader("REFUND INFORMATION");
            System.out.println("Booking ID: " + id);
            System.out.println("Amount Paid: Rs." + refundAmount);
            System.out.println("Refund Amount: Rs." + refundAmount);
            printSeparator();
            printInfo("Refund will be processed within 5-7 business days.");

        } catch (BookingNotFoundException e) {
            printError(e.getMessage());
        } catch (Exception e) {
            printError(e.getMessage());
        }
    }

    public void viewMyBookings(int userId) {
        printSubHeader("MY BOOKINGS");
        
        if (userId <= 0) {
            printInfo("Please login to view your bookings.");
            return;
        }
        
        List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId);
        
        if (userBookings.isEmpty()) {
            printInfo("You have no bookings yet.");
            return;
        }
        
        printHeader("YOUR BOOKINGS");
        
        for (Booking b : userBookings) {
            Movie movie = movieDAO.getMovieById(b.getMovieId());
            Theatre theatre = theatreDAO.getTheatreById(b.getTheatreId());
            
            String movieName = (movie != null) ? movie.getTitle() : "Unknown";
            String theatreName = (theatre != null) ? theatre.getName() : "Unknown";
            
            System.out.println("| Booking ID: " + b.getBookingId());
            System.out.println("| Movie: " + movieName);
            System.out.println("| Theatre: " + theatreName);
            System.out.println("| Showtime: " + b.getShowtime());
            System.out.println("| Seats: " + String.join(", ", b.getSeatLabels()));
            System.out.println("| Amount: Rs." + b.getTotalAmount());
            System.out.println("| Status: " + b.getStatus());
            printSeparator();
        }
    }
}