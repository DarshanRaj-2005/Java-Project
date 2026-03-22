package org.expleo.TicketBookingJavaProject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.service.SeatService;

public class SeatController {
	private SeatService seatService = new SeatService();
	private Scanner sc = new Scanner(System.in);
	
    public void showSeatSelectionPage() {
        displaySeatLayout();
        displayAvailableSeats();
        displayBookedSeats();
    }

    public void displaySeatLayout() {
        List<Seat> seats = seatService.getSeatLayout();

        System.out.println("\n========== SEAT SELECTION PAGE ==========");
        System.out.println("SCREEN\n");

        String currentRow = "";

        for (Seat seat : seats) {
            if (!seat.getRow().equals(currentRow)) {
                currentRow = seat.getRow();
                System.out.print(currentRow + "  ");
            }

            String symbol = seat.getStatus().equalsIgnoreCase("BOOKED") ? "[X]" : "[O]";
            System.out.print(seat.getSeatLabel() + symbol + "  ");

            if (seat.getNumber() == 10) {
                System.out.println();
            }
        }
    }

    public void displayAvailableSeats() {
        List<Seat> availableSeats = seatService.getAvailableSeats();

        System.out.println("\nAvailable Seats:");
        if (availableSeats.isEmpty()) {
            System.out.println("No seats available.");
            return;
        }

        for (Seat seat : availableSeats) {
            System.out.print(seat.getSeatLabel() + " ");
        }
        System.out.println();
    }

    public void displayBookedSeats() {
        List<Seat> bookedSeats = seatService.getBookedSeats();

        System.out.println("\nBooked Seats:");
        if (bookedSeats.isEmpty()) {
            System.out.println("No seats booked.");
            return;
        }

        for (Seat seat : bookedSeats) {
            System.out.print(seat.getSeatLabel() + " ");
        }
        System.out.println();
    }

    public Seat selectSingleSeat() {
        System.out.print("\nEnter seat number: ");
        String seatLabel = sc.nextLine().trim().toUpperCase();

        String validationMessage = seatService.validateSingleSeatSelection(seatLabel);

        if (!validationMessage.equals("VALID")) {
            System.out.println(validationMessage);
            return null;
        }

        boolean booked = seatService.bookSeat(seatLabel);

        if (booked) {
            Seat seat = seatService.getSeatByLabel(seatLabel);
            System.out.println("Seat selected successfully: " + seat.getSeatLabel());
            return seat;
        } else {
            System.out.println("Seat booking failed. Please try again.");
            return null;
        }
    }

    public List<Seat> selectMultipleSeats(int ticketCount) {
        System.out.print("\nEnter " + ticketCount + " seat number(s) separated by comma: ");
        String input = sc.nextLine();

        String[] seatArray = input.split(",");
        List<String> seatLabels = new ArrayList<>();

        for (String seat : seatArray) {
            String seatLabel = seat.trim().toUpperCase();
            if (!seatLabel.isEmpty()) {
                seatLabels.add(seatLabel);
            }
        }

        String validationMessage = seatService.validateMultipleSeatSelection(seatLabels, ticketCount);

        if (!validationMessage.equals("VALID")) {
            System.out.println(validationMessage);
            return null;
        }

        List<Seat> selectedSeats = seatService.bookMultipleSeats(seatLabels);

        if (selectedSeats == null) {
            System.out.println("Seat booking failed. Please try again.");
            return null;
        }

        System.out.print("Seats selected successfully: ");
        for (Seat seat : selectedSeats) {
            System.out.print(seat.getSeatLabel() + " ");
        }
        System.out.println();

        return selectedSeats;
    }
}
