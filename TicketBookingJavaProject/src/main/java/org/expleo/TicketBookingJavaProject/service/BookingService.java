package org.expleo.TicketBookingJavaProject.service;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class BookingService {
	private SeatRepositoryImpl repo = new SeatRepositoryImpl();

    public boolean validateTicketCount(int ticketCount) {

        List<Seat> seats = repo.getSeats();

        int availableSeats = 0;

        for (Seat seat : seats) {
            if (seat.getStatus().equals("AVAILABLE")) {
                availableSeats++;
            }
        }

        return ticketCount <= availableSeats;
    }
}
