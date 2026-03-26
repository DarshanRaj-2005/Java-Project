package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.service.BookingService;

import java.util.*;

public class BookingController {

    private BookingService service = new BookingService();
    private SeatController seatController = new SeatController();

    // 🎟️ Step 1: Check ticket availability
    public void checkTicketAvailability(int ticketCount) {

        boolean isAvailable = service.validateTicketCount(ticketCount);

        if (!isAvailable) {
            System.out.println("Requested ticket count is invalid or not enough seats are available.");
            return;
        }

        System.out.println("Tickets available. Proceed to seat selection.");
        seatController.showSeatSelectionPage();

        List<Seat> selectedSeats = new ArrayList<>();

        // 🎯 Single seat selection
        if (ticketCount == 1) {

            Seat selectedSeat = null;

            while (selectedSeat == null) {
                selectedSeat = seatController.selectSingleSeat();
            }

            selectedSeats.add(selectedSeat);

        } else {

            // 🎯 Multiple seat selection
            while (selectedSeats.isEmpty()) {

                List<Seat> seats = seatController.selectMultipleSeats(ticketCount);

                if (seats != null) {
                    selectedSeats = seats;
                }
            }
        }

        confirmSeatBooking(ticketCount, selectedSeats);
    }

    // 🎟️ Step 2: Confirm selected seats
    public void confirmSeatBooking(int ticketCount, List<Seat> selectedSeats) {

        System.out.println("\n========== BOOKING DETAILS ==========");
        System.out.println("Ticket Count : " + ticketCount);

        System.out.print("Selected Seats : ");
        for (Seat seat : selectedSeats) {
            System.out.print(seat.getSeatLabel() + " ");
        }
        System.out.println();

        System.out.println("Message : Booking confirmed successfully.");
    }
    public void confirmBooking(Booking booking, Payment payment) {
        service.confirmBooking(booking, payment);
    }
}