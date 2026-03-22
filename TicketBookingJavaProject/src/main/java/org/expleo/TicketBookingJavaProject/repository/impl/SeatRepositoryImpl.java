package org.expleo.TicketBookingJavaProject.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;

public class SeatRepositoryImpl {
	private static List<Seat> seats = new ArrayList<>();

    static {
        for (char row = 'A'; row <= 'J'; row++) {
            for (int number = 1; number <= 10; number++) {
                seats.add(new Seat(String.valueOf(row), number, "AVAILABLE"));
            }
        }

        markBooked("A", 5);
        markBooked("B", 3);
        markBooked("B", 7);
        markBooked("C", 4);
        markBooked("C", 8);
        markBooked("D", 2);
        markBooked("D", 6);
        markBooked("E", 5);
        markBooked("E", 9);
        markBooked("F", 3);
        markBooked("F", 8);
        markBooked("G", 2);
        markBooked("G", 6);
        markBooked("H", 4);
        markBooked("H", 7);
        markBooked("I", 3);
        markBooked("I", 8);
        markBooked("J", 2);
        markBooked("J", 7);
    }

    private static void markBooked(String row, int number) {
        for (Seat seat : seats) {
            if (seat.getRow().equals(row) && seat.getNumber() == number) {
                seat.setStatus("BOOKED");
                break;
            }
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
