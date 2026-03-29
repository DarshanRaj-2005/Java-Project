package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.model.PaymentResponse;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import org.expleo.TicketBookingJavaProject.util.PaymentUtil;
import org.expleo.TicketBookingJavaProject.util.BookingIdGenerator;

import java.util.*;

/*
 * BookingController
 * Handles seat selection, payment, and booking confirmation
 */
public class BookingController {

    // Service layer
    private BookingService service = new BookingService();

    // Seat selection controller
    private SeatController seatController = new SeatController();

    // Single Scanner instance
    private Scanner sc = new Scanner(System.in);

    /*
     * Validate ticket count
     */
    public boolean validateTickets(int count) {
        return service.validateTicketCount(count);
    }

    /*
     * Main booking flow
     */
    public void startBooking(int ticketCount) {

        // Step 1: Validate ticket count
        if (!validateTickets(ticketCount)) {
            System.out.println("Invalid ticket count or insufficient seats.");
            return;
        }

        // Step 2: Show seats
        seatController.showSeatSelectionPage();

        List<Seat> selectedSeats = new ArrayList<>();

        // Step 3: Seat selection
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

        // Step 4: Process booking
        processBooking(ticketCount, selectedSeats);
    }

    /*
     * Booking + Payment Process
     */
    private void processBooking(int ticketCount, List<Seat> seats) {

        // Display selected seats
        System.out.println("\nSelected Seats:");
        for (Seat s : seats) {
            System.out.print(s.getSeatLabel() + " ");
        }

        // Calculate amount
        double amount = ticketCount * 150;
        System.out.println("\nAmount: Rs." + amount);

        // Confirm booking
        System.out.print("Confirm Booking? (yes/no): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Booking Cancelled.");
            return;
        }

        // Step 5: Payment processing
        PaymentResponse response = PaymentUtil.processPayment(amount);

        System.out.println("Txn ID: " + response.getTransactionId());
        System.out.println("Status: " + response.getStatus());

        // If payment fails
        if (!response.getStatus().equalsIgnoreCase("SUCCESS")) {
            System.out.println("Payment Failed.");
            return;
        }

        // Step 6: Confirm seats (mark BOOKED)
        seatController.confirmSeats(seats);

        // Step 7: Generate Booking ID
        String bookingId = BookingIdGenerator.generateBookingId();

        // Step 8: Create Booking object
        Booking booking = new Booking();
        
        booking.setBookingId(bookingId);
        booking.setTotalAmount(amount);
        booking.setStatus("CONFIRMED");

        // Step 9: Create Payment object
        Payment payment = new Payment();
        payment.setPaymentId(new Random().nextInt(10000)); // simple ID generation
        payment.setAmount(amount);
        payment.setMethod("UPI"); // default method
        payment.setStatus(response.getStatus());

        // Step 10: Confirm booking in service layer
        confirmBooking(booking, payment);

        // Final Output
        System.out.println("\nBooking Confirmed!");
        System.out.println("Booking ID: " + booking.getBookingId());
    }

    /*
     * Final confirmation
     */
    public void confirmBooking(Booking booking, Payment payment) {
        service.confirmBooking(booking, payment);
    }

    /*
     * Optional direct booking
     */
    public void bookSeats(int count) {
        service.bookSeats(count);
    }
}