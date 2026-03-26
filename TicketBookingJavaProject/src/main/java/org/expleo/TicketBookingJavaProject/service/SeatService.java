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
        List<Seat> availableSeats = new ArrayList<>();

        for (Seat seat : repo.getSeats()) {
            if ("AVAILABLE".equalsIgnoreCase(seat.getStatus())) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }

    public List<Seat> getBookedSeats() {
        List<Seat> bookedSeats = new ArrayList<>();

        for (Seat seat : repo.getSeats()) {
            if ("BOOKED".equalsIgnoreCase(seat.getStatus())) {
                bookedSeats.add(seat);
            }
        }
        return bookedSeats;
    }

    public Seat getSeatByLabel(String seatLabel) {
        for (Seat seat : repo.getSeats()) {
            if (seat.getSeatLabel().equalsIgnoreCase(seatLabel)) {
                return seat;
            }
        }
        return null;
    }

    public boolean bookSeat(String seatLabel) {
        Seat seat = getSeatByLabel(seatLabel);

        if (seat != null && "AVAILABLE".equalsIgnoreCase(seat.getStatus())) {
            seat.setStatus("BOOKED");
            return true;
        }
        return false;
    }

    public List<Seat> bookMultipleSeats(List<String> seatLabels) {

        List<Seat> selectedSeats = new ArrayList<>();

        // Validate all seats first
        for (String label : seatLabels) {
            Seat seat = getSeatByLabel(label);

            if (seat == null || !"AVAILABLE".equalsIgnoreCase(seat.getStatus())) {
                return null;
            }
        }

        // Book seats
        for (String label : seatLabels) {
            Seat seat = getSeatByLabel(label);
            seat.setStatus("BOOKED");
            selectedSeats.add(seat);
        }

        return selectedSeats;
    }

    // ⭐ BOOK FIRST AVAILABLE SEATS BY COUNT
    public void bookSeats(int count) {

        int booked = 0;

        for (Seat seat : repo.getSeats()) {

            if ("AVAILABLE".equalsIgnoreCase(seat.getStatus())) {
                seat.setStatus("BOOKED");
                booked++;
            }

            if (booked == count) break;
        }

        System.out.println(booked + " seats booked.");
    }
}