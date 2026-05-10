package org.expleo.TicketBookingJavaProject.repository.impl;

import java.util.List;

import org.expleo.TicketBookingJavaProject.model.Seat;


public interface SeatRepository {
	List<Seat> getSeatsByShowId(String sessionKey);

    Seat findSeatByLabel(String sessionKey, String seatLabel);

    boolean updateSeatBookingStatus(int seatId, boolean booked);
}
