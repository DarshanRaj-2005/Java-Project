package org.expleo.TicketBookingJavaProject.controller;

import java.util.List;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.service.SeatService;

public class SeatController {
	private SeatService seatService = new SeatService();

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

            String symbol = seat.getStatus().equals("BOOKED") ? "[X]" : "[O]";
            System.out.print(seat.getSeatLabel() + symbol + "  ");

            if (seat.getNumber() == 10) {
                System.out.println();
            }
        }
    }

    public void displayAvailableSeats() {
        List<Seat> availableSeats = seatService.getAvailableSeats();

        System.out.println("\nAvailable Seats:");
        for (Seat seat : availableSeats) {
            System.out.print(seat.getSeatLabel() + " ");
        }
        System.out.println();
    }

    public void displayBookedSeats() {
        List<Seat> bookedSeats = seatService.getBookedSeats();

        System.out.println("\nBooked Seats:");
        for (Seat seat : bookedSeats) {
            System.out.print(seat.getSeatLabel() + " ");
        }
        System.out.println();
    }
}
