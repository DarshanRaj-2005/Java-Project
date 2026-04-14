package org.expleo.TicketBookingJavaProject.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.expleo.TicketBookingJavaProject.service.BookingService;
import org.expleo.TicketBookingJavaProject.service.PaymentService;
import org.expleo.TicketBookingJavaProject.service.SeatService;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

public class BookingController {

	private Scanner sc = new Scanner(System.in);

	private SeatService seatService;
	private BookingService bookingService;
	private PaymentService paymentService;

	public BookingController() {
		seatService = new SeatService();
		bookingService = new BookingService();
		paymentService = new PaymentService();
	}

	public void startBooking(Movie movie, Theatre theatre, String city, int userId) {

		String showtime = selectShowtime();

		if (showtime == null) {
			return;
		}

		bookSeats(movie, theatre, city, showtime, userId);
	}

	private String selectShowtime() {

        List<String> shows = Arrays.asList(
                "10:00 AM",
                "01:30 PM",
                "06:00 PM",
                "10:00 PM"
        );

        System.out.println("========== SELECT SHOWTIME ==========");

        for (int i = 0; i < shows.size(); i++) {
            System.out.println((i + 1) + ". " + shows.get(i));
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > shows.size()) {
            System.out.println("Invalid showtime selection.");
            return null;
        }

        return shows.get(choice - 1);
    }

	private void bookSeats(Movie movie, Theatre theatre, String city, String showtime, int userId) {

    	String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime;
        System.out.print("Enter number of tickets: ");
        int ticketCount = InputUtil.getIntInput(sc);

        if (ticketCount <= 0 || ticketCount > 10) {
            System.out.println("Invalid ticket count. Maximum 10 allowed.");
            return;
        }

            seatService.displaySeatLayout(sessionKey);

            List<Seat> selectedSeats = new ArrayList<>();
            Set<String> selectedLabels = new HashSet<>();

            while (selectedSeats.size() < ticketCount) {

                System.out.print("Enter Seat Label (Example A1): ");
                String label = sc.nextLine().trim().toUpperCase();

                if (selectedLabels.contains(label)) {
                    System.out.println("Seat already selected.");
                    continue;
                }

                Seat seat = bookingService.findSeatByLabel(sessionKey, label);

                if (seat == null) {
                    System.out.println("Invalid seat label.");
                    continue;
                }

                if (seat.isBooked()) {
                    System.out.println("Seat already booked.");
                    continue;
                }

                selectedSeats.add(seat);
                selectedLabels.add(label);

                System.out.println(label + " selected successfully.");
            }
            List<String> seatLabels = new ArrayList<>();
            double totalAmount = 0;

            for (Seat seat : selectedSeats) {
                seatLabels.add(seat.getSeatLabel());
                totalAmount += seat.getPrice();
            }

            BillDetails bill = new BillDetails(ticketCount, totalAmount, 3.5, 10);

            System.out.println("========== BOOKING SUMMARY ==========");
            System.out.println("Movie      : " + movie.getTitle());
            System.out.println("Theatre    : " + theatre.getName() + " (" + city + ")");
            System.out.println("Showtime   : " + showtime);
            System.out.println("Seats      : " + String.join(", ", seatLabels));
            bill.printBill();

            System.out.print("Proceed to payment? (yes/no): ");
            String proceed = sc.nextLine().trim();

            if (!proceed.equalsIgnoreCase("yes")) {
                System.out.println("Booking cancelled.");
                return;
            }

            boolean paymentSuccess = processPayment(bill.getTotalAmount());

            if (!paymentSuccess) {
                System.out.println("Payment failed. Booking cancelled.");
                return;
            }

            Booking booking = new Booking();
            booking.setBookingId("BK" + System.currentTimeMillis());
            booking.setUserId(String.valueOf(userId));
            booking.setMovieId(movie.getId());
            booking.setTheatreId(theatre.getId());
            booking.setShowtime(showtime);
            booking.setSessionKey(sessionKey);
            booking.setSeatLabels(seatLabels);
            booking.setBookedSeats(selectedSeats);
            booking.setTotalAmount(bill.getTotalAmount());
            booking.setStatus("CONFIRMED");

            boolean success = bookingService.confirmBooking(booking);

            if (success) {
                System.out.println("====================================");
                System.out.println("      BOOKING CONFIRMED!            ");
                System.out.println("====================================");
                System.out.println("Booking ID : " + booking.getBookingId());
                System.out.println("Movie      : " + movie.getTitle());
                System.out.println("Theatre    : " + theatre.getName());
                System.out.println("Showtime   : " + booking.getShowtime());
                System.out.println("Seats      : " + String.join(", ", booking.getSeatLabels()));
                System.out.println("Amount     : Rs." + booking.getTotalAmount());
                System.out.println("Status     : " + booking.getStatus());
                System.out.println("====================================");
            } else {
                System.out.println("Booking failed.");
            }
        }

        private boolean processPayment(double amount) {

            while (true) {

            System.out.println("========== PAYMENT ==========");
            System.out.println("1. Card");
            System.out.println("2. UPI");
            System.out.println("3. Cash");

            System.out.print("Choice: ");
            int choice = InputUtil.getIntInput(sc);

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Enter Card Number: ");
                        String card = sc.nextLine();

                        System.out.print("Enter CVV: ");
                        String cvv = sc.nextLine();

                        paymentService.validateCardPayment(card, cvv);
                        return true;

                    case 2:
                        System.out.print("Enter UPI ID: ");
                        String upi = sc.nextLine();

                        paymentService.validateUpiPayment(upi);
                        return true;

                    case 3:
                        return true;

                    default:
                        System.out.println("Invalid payment choice.");
                }

            } catch (Exception e) {
                System.out.println("Payment Error: " + e.getMessage());
            }
        }

	}

	public void cancelBooking(String bookingId) {

		try {
			boolean cancelled = bookingService.cancelBooking(bookingId);

			if (!cancelled) {
				throw new BookingNotFoundException("Booking with ID " + bookingId + " not found");
			}

			System.out.println("Booking cancelled successfully.");

		} catch (BookingNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}