package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
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

            if (seat.getNumber() == 10) System.out.println();
        }
    }

    public void displayAvailableSeats() {
        System.out.println("\nAvailable Seats:");
        for (Seat s : seatService.getAvailableSeats()) {
            System.out.print(s.getSeatLabel() + " ");
        }
        System.out.println();
    }

    public void displayBookedSeats() {
        System.out.println("\nBooked Seats:");
        for (Seat s : seatService.getBookedSeats()) {
            System.out.print(s.getSeatLabel() + " ");
        }
        System.out.println();
    }

    public Seat selectSingleSeat() {
        System.out.print("\nEnter seat number: ");
        String label = sc.nextLine().toUpperCase();

        String validation = seatService.validateSingleSeatSelection(label);

        if (!validation.equals("VALID")) {
            System.out.println(validation);
            return null;
        }

        Seat seat = seatService.getSeatByLabel(label);
        System.out.println("Seat selected: " + seat.getSeatLabel());

        return seat;
    }

    public List<Seat> selectMultipleSeats(int count) {

        System.out.print("\nEnter seats (comma separated): ");
        String input = sc.nextLine();

        List<String> labels = new ArrayList<>();
        for (String s : input.split(",")) {
            labels.add(s.trim().toUpperCase());
        }

        String validation = seatService.validateMultipleSeatSelection(labels, count);

        if (!validation.equals("VALID")) {
            System.out.println(validation);
            return null;
        }

        List<Seat> result = new ArrayList<>();
        for (String label : labels) {
            result.add(seatService.getSeatByLabel(label));
        }

        return result;
    }

    public void confirmSeats(List<Seat> seats) {
        for (Seat s : seats) {
            s.setStatus("BOOKED");
        }
    }
}