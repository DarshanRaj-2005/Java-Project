package org.expleo.TicketBookingJavaProject.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;

public class SeatRepositoryImpl {
	public List<Seat> getSeats() {

        List<Seat> seats = new ArrayList<>();

        char[] rows = {'A','B','C','D','E','F','G','H','I','J'};

        for (char row : rows) {
            for (int num = 1; num <= 10; num++) {

                String status = "AVAILABLE";

                if (
                    (row == 'A' && num == 5) ||
                    (row == 'B' && (num == 3 || num == 7)) ||
                    (row == 'C' && (num == 4 || num == 8)) ||
                    (row == 'D' && (num == 2 || num == 6)) ||
                    (row == 'E' && (num == 5 || num == 9)) ||
                    (row == 'F' && (num == 3 || num == 8)) ||
                    (row == 'G' && (num == 2 || num == 6)) ||
                    (row == 'H' && (num == 4 || num == 7)) ||
                    (row == 'I' && (num == 3 || num == 8)) ||
                    (row == 'J' && (num == 2 || num == 7))
                ) {
                    status = "BOOKED";
                }

                seats.add(new Seat(String.valueOf(row), num, status));
            }
        }

        return seats;
    }
}
