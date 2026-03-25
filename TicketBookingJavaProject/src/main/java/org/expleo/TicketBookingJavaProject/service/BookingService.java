package org.expleo.TicketBookingJavaProject.service;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class BookingService {

    private SeatRepositoryImpl repo = new SeatRepositoryImpl();

    public boolean validateTicketCount(int ticketCount) {

        if (ticketCount <= 0) return false;

        int available = 0;

        for (Seat s : repo.getSeats()) {
            if (s.getStatus().equalsIgnoreCase("AVAILABLE")) {
                available++;
            }
        }

        return ticketCount <= available;
    }
}