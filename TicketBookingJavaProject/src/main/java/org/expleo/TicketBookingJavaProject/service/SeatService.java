package org.expleo.TicketBookingJavaProject.service;

import java.util.ArrayList;
import java.util.List;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class SeatService {
	private SeatRepositoryImpl repo = new SeatRepositoryImpl();

    public List<Seat> getSeatLayout() {
        return repo.getSeats();
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> seats = repo.getSeats();
        List<Seat> availableSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (seat.getStatus().equals("AVAILABLE")) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }

    public List<Seat> getBookedSeats() {
        List<Seat> seats = repo.getSeats();
        List<Seat> bookedSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (seat.getStatus().equals("BOOKED")) {
                bookedSeats.add(seat);
            }
        }
        return bookedSeats;
    }
}
