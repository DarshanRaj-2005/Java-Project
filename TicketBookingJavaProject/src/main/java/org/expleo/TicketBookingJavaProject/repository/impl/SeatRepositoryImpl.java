package org.expleo.TicketBookingJavaProject.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;

/*
 * Repository class simulating database for seats
 * Stores and manages seat availability in theatre format (A1, B2, etc.)
 */
public class SeatRepositoryImpl {

    // Static list to act as in-memory database
    private static List<Seat> seats = new ArrayList<>();

    // Static block to initialize seats
    static {
        int seatId = 1;

        // Create seats from row A to J and numbers 1 to 10
        for (char r = 'A'; r <= 'J'; r++) {
            for (int i = 1; i <= 10; i++) {

                // Create seat using correct constructor
                seats.add(new Seat(seatId++, String.valueOf(r), i, "AVAILABLE"));
            }
        }
    }

    // Get all seats
    public static List<Seat> getSeats() {
        return seats;
    }
}