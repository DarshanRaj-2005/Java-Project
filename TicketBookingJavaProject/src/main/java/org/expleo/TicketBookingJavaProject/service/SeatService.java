package org.expleo.TicketBookingJavaProject.service;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class SeatService {

    private SeatRepositoryImpl repo = new SeatRepositoryImpl();

    public List<Seat> getSeatLayout() {
        return repo.getSeats();
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> result = new ArrayList<>();
        for (Seat s : repo.getSeats()) {
            if (s.getStatus().equalsIgnoreCase("AVAILABLE")) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Seat> getBookedSeats() {
        List<Seat> result = new ArrayList<>();
        for (Seat s : repo.getSeats()) {
            if (s.getStatus().equalsIgnoreCase("BOOKED")) {
                result.add(s);
            }
        }
        return result;
    }

    public Seat getSeatByLabel(String label) {
        for (Seat s : repo.getSeats()) {
            if (s.getSeatLabel().equalsIgnoreCase(label)) {
                return s;
            }
        }
        return null;
    }

    public String validateSingleSeatSelection(String label) {
        Seat seat = getSeatByLabel(label);

        if (seat == null) return "Invalid seat.";
        if (seat.getStatus().equalsIgnoreCase("BOOKED")) return "Seat already booked.";

        return "VALID";
    }

    public String validateMultipleSeatSelection(List<String> labels, int count) {

        if (labels.size() != count) {
            return "Seat count mismatch.";
        }

        Set<String> uniqueSeats = new HashSet<>(labels);
        if (uniqueSeats.size() != labels.size()) {
            return "Duplicate seats selected. Please select unique seats.";
        }

        for (String label : labels) {

            Seat seat = getSeatByLabel(label);

            if (seat == null) {
                return "Invalid seat: " + label;
            }

            if (seat.getStatus().equalsIgnoreCase("BOOKED")) {
                return "Seat already booked: " + label;
            }
        }

        return "VALID";
    }
}