package org.expleo.TicketBookingJavaProject.service;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class BookingService {

	private SeatRepositoryImpl repo = new SeatRepositoryImpl();

	public boolean validateTicketCount(int ticketCount) {

		if (ticketCount <= 0) {
			return false;
		}

		List<Seat> seats = repo.getSeats();
		int availableSeats = 0;

		for (Seat seat : seats) {
			if (seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
				availableSeats++;
			}
		}

		return ticketCount <= availableSeats;
	}
}
