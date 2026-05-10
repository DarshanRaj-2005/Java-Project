package org.expleo.TicketBookingJavaProject.service;

import java.util.List;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepository;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class SeatService {

	private SeatRepository seatRepository = new SeatRepositoryImpl();

	public List<Seat> getAvailableSeats(String sessionKey) {
	    return seatRepository.getSeatsByShowId(sessionKey);
	}

	public Seat findSeatByLabel(String sessionKey, String seatLabel) {
	    return seatRepository.findSeatByLabel(sessionKey, seatLabel);
	}

    public void displaySeatLayout(String sessionKey) {

    	List<Seat> seats = getAvailableSeats(sessionKey);

        System.out.println("\n========== SCREEN ==========");

        for (Seat seat : seats) {

            if (seat.isBooked()) {
                System.out.printf("[XX] ");
            } else {
                System.out.printf("[%s] ", seat.getSeatLabel());
            }

            if (seat.getSeatLabel().endsWith("5")) {
                System.out.println();
            }
        }

        System.out.println();
    }
}
