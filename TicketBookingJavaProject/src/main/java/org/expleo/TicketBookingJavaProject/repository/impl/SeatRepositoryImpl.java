package org.expleo.TicketBookingJavaProject.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;

public class SeatRepositoryImpl {

    private static List<Seat> seats = new ArrayList<>();

    static {
        for (char r = 'A'; r <= 'J'; r++) {
            for (int i = 1; i <= 10; i++) {
                seats.add(new Seat(String.valueOf(r), i, "AVAILABLE"));
            }
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }
}