package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import java.util.*;

public class BookingController {

	private BookingService service = new BookingService();

	private SeatController seatController = new SeatController();

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