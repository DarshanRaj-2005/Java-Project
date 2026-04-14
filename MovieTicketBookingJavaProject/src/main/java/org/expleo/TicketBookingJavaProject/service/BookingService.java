package org.expleo.TicketBookingJavaProject.service;



import java.util.ArrayList;
import java.util.List;

import org.expleo.TicketBookingJavaProject.exception.BookingNotFoundException;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepository;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepository;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class BookingService {
	private BookingRepository bookingRepository;
    private SeatRepository seatRepository;

    public BookingService() {
        bookingRepository = new BookingRepositoryImpl();
        seatRepository = new SeatRepositoryImpl();
    }

    public Seat findSeatByLabel(String sessionKey, String seatLabel) {
        return seatRepository.findSeatByLabel(sessionKey, seatLabel);
    }

    public boolean confirmBooking(Booking booking) {

        double totalAmount = 0;
        List<String> seatLabels = new ArrayList<>();

        // Validate each selected seat
        for (Seat seat : booking.getBookedSeats()) {

            Seat dbSeat = seatRepository.findSeatByLabel(
                    booking.getSessionKey(),
                    seat.getSeatLabel()
            );

            // Seat not found
            if (dbSeat == null) {
                System.out.println("Seat " + seat.getSeatLabel() + " does not exist.");
                return false;
            }

            // Already booked
            if (dbSeat.isBooked()) {
                System.out.println("Seat " + seat.getSeatLabel() + " is already booked.");
                return false;
            }

            totalAmount += dbSeat.getPrice();
            seatLabels.add(dbSeat.getSeatLabel());

            // Replace with DB seat object so seatId is available
            seat.setSeatId(dbSeat.getSeatId());
            seat.setPrice(dbSeat.getPrice());
            seat.setStatus(dbSeat.getStatus());
        }

        booking.setSeatLabels(seatLabels);
        booking.setTotalAmount(totalAmount);
        booking.setStatus("CONFIRMED");

        return bookingRepository.saveBooking(booking);
    }
    public boolean cancelBooking(String bookingId) throws BookingNotFoundException {

        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new BookingNotFoundException("Invalid booking ID");
        }

        // Temporary implementation
        // Later connect with repository/DAO cancel method
        return true;
    }
}
