package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.model.PaymentResponse;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import org.expleo.TicketBookingJavaProject.util.PaymentUtil;
import org.expleo.TicketBookingJavaProject.util.BookingIdGenerator;

import java.util.*;

public class BookingController {

    private BookingService service = new BookingService();
    private SeatController seatController = new SeatController();

    public void checkTicketAvailability(int ticketCount) {

        if (!service.validateTicketCount(ticketCount)) {
            System.out.println("Invalid ticket count or insufficient seats.");
            return;
        }

        seatController.showSeatSelectionPage();

        List<Seat> selectedSeats = new ArrayList<>();

        if (ticketCount == 1) {
            while (selectedSeats.isEmpty()) {
                Seat s = seatController.selectSingleSeat();
                if (s != null) selectedSeats.add(s);
            }
        } else {
            while (selectedSeats.isEmpty()) {
                List<Seat> s = seatController.selectMultipleSeats(ticketCount);
                if (s != null) selectedSeats = s;
            }
        }

        confirmBooking(ticketCount, selectedSeats);
    }

    public void confirmBooking(int ticketCount, List<Seat> seats) {

        System.out.println("\nSelected Seats:");
        for (Seat s : seats) System.out.print(s.getSeatLabel() + " ");

        double amount = ticketCount * 150;
        System.out.println("\nAmount: Rs." + amount);

        Scanner sc = new Scanner(System.in);
        System.out.print("Confirm Booking? (yes/no): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled.");
            return;
        }

        PaymentResponse response = PaymentUtil.processPayment(amount);

        System.out.println("Txn ID: " + response.getTransactionId());
        System.out.println("Status: " + response.getStatus());

        if (!response.getStatus().equalsIgnoreCase("SUCCESS")) {
            System.out.println("Payment Failed.");
            return;
        }

        seatController.confirmSeats(seats);

        String bookingId = BookingIdGenerator.generateBookingId();

        System.out.println("\nBooking Confirmed!");
        System.out.println("Booking ID: " + bookingId);
    }
}