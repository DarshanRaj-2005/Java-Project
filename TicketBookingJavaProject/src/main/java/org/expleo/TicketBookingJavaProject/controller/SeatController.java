package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.service.SeatService;

/*
 * SeatController
 * Handles seat display, selection, and confirmation
 * Acts as UI layer for seat-related operations
 */
public class SeatController {

	// Service layer to handle seat logic
	private SeatService seatService = new SeatService();

	// Scanner for user input
	private Scanner sc = new Scanner(System.in);

	/*
	 * Displays complete seat selection page Includes layout + available seats +
	 * booked seats
	 */
	public void showSeatSelectionPage(String sessionKey) {
		displaySeatLayout(sessionKey);
		displayAvailableSeats(sessionKey);
		displayBookedSeats(sessionKey);
	}

	/*
	 * Displays theatre seat layout (A1, A2, etc.) [O] = Available, [X] = Booked
	 */
	public void displaySeatLayout(String sessionKey) {
		List<Seat> seats = seatService.getSeatLayout(sessionKey);

		System.out.println("\n========== SEAT SELECTION PAGE ==========");
		System.out.println("SCREEN\n");

		String currentRow = "";

		// Loop through seats and print row-wise
		for (Seat seat : seats) {

			// Print new row label (A, B, C...)
			if (!seat.getRow().equals(currentRow)) {
				currentRow = seat.getRow();
				System.out.print(currentRow + "  ");
			}

			// Show status symbol
			String symbol = seat.getStatus().equalsIgnoreCase("BOOKED") ? "[X]" : "[O]";

			// Print seat label with status
			System.out.print(seat.getSeatLabel() + symbol + "  ");

			// Move to next line after 10 seats
			if (seat.getNumber() == 10)
				System.out.println();
		}
	}

	/*
	 * Displays all available seats
	 */
	public void displayAvailableSeats(String sessionKey) {
		System.out.println("\nAvailable Seats:");

		for (Seat s : seatService.getAvailableSeats(sessionKey)) {
			System.out.print(s.getSeatLabel() + " ");
		}

		System.out.println();
	}

	/*
	 * Displays all booked seats
	 */
	public void displayBookedSeats(String sessionKey) {
		System.out.println("\nBooked Seats:");

		for (Seat s : seatService.getBookedSeats(sessionKey)) {
			System.out.print(s.getSeatLabel() + " ");
		}

		System.out.println();
	}

	/*
	 * Handles single seat selection
	 * 
	 * @return selected Seat or null if invalid
	 */
	public Seat selectSingleSeat(String sessionKey) {

		// Take input from user
		System.out.print("\nEnter seat number: ");
		String label = sc.nextLine().toUpperCase();

		// Validate seat
		String validation = seatService.validateSingleSeatSelection(sessionKey, label);

		// If invalid, show error
		if (!validation.equals("VALID")) {
			System.out.println(validation);
			return null;
		}

		// Fetch seat object
		Seat seat = seatService.getSeatByLabel(sessionKey, label);

		System.out.println("Seat selected: " + seat.getSeatLabel());

		return seat;
	}

	/*
	 * Handles multiple seat selection
	 * 
	 * @param count number of seats required
	 * 
	 * @return list of selected seats or null if invalid
	 */
	public List<Seat> selectMultipleSeats(String sessionKey, int count) {

		// Take input (comma-separated)
		System.out.print("\nEnter seats (comma separated): ");
		String input = sc.nextLine();

		// Convert input into list
		List<String> labels = new ArrayList<>();
		for (String s : input.split(",")) {
			labels.add(s.trim().toUpperCase());
		}

		// Validate selection
		String validation = seatService.validateMultipleSeatSelection(sessionKey, labels, count);

		if (!validation.equals("VALID")) {
			System.out.println(validation);
			return null;
		}

		// Fetch seat objects
		List<Seat> result = new ArrayList<>();
		for (String label : labels) {
			result.add(seatService.getSeatByLabel(sessionKey, label));
		}

		return result;
	}

	/*
	 * Confirms selected seats by marking them as BOOKED
	 */
	public void confirmSeats(List<Seat> seats) {
		for (Seat s : seats) {
			s.setStatus("BOOKED");
		}
	}
}