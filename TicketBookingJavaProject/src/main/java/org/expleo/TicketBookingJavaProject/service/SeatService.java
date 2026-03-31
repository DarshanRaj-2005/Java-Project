package org.expleo.TicketBookingJavaProject.service;


import java.util.ArrayList;
import java.util.List;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

public class SeatService {

    private SeatRepositoryImpl repo = new SeatRepositoryImpl();

=======
import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

/*
 * SeatService Class
 * Handles all seat-related business logic
 * - Fetch seat layout
 * - Filter available/booked seats
 * - Validate seat selection
 */
public class SeatService {

    // Repository to access seat data
    private static SeatRepositoryImpl repo = new SeatRepositoryImpl();

    /*
     * Returns complete seat layout
     */
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
    }
    /*
     * Returns list of available seats
     */
    public List<Seat> getAvailableSeats() {
        List<Seat> result = new ArrayList<>();

        // Loop through all seats and filter AVAILABLE ones
        for (Seat s : repo.getSeats()) {
            if (s.getStatus().equalsIgnoreCase("AVAILABLE")) {
                result.add(s);
            }
        }
        return result;
    }

    /*
     * Returns list of booked seats
     */
    public List<Seat> getBookedSeats() {
        List<Seat> result = new ArrayList<>();

        // Loop through all seats and filter BOOKED ones
        for (Seat s : repo.getSeats()) {
            if (s.getStatus().equalsIgnoreCase("BOOKED")) {
                result.add(s);
            }
        }
        return result;
    }

    /*
     * Fetch a seat using seat label (e.g., A1, B5)
     * @param label seat label
     * @return Seat object or null if not found
     */
    public Seat getSeatByLabel(String label) {
        for (Seat s : repo.getSeats()) {
            if (s.getSeatLabel().equalsIgnoreCase(label)) {
                return s;
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
    /*
     * Validates single seat selection
     * @param label seat label entered by user
     * @return "VALID" or error message
     */
    public String validateSingleSeatSelection(String label) {

        Seat seat = getSeatByLabel(label);

        // Check if seat exists
        if (seat == null) return "Invalid seat.";

        // Check if already booked
        if (seat.getStatus().equalsIgnoreCase("BOOKED")) {
            return "Seat already booked.";
        }

        return "VALID";
    }

    /*
     * Validates multiple seat selection
     * @param labels list of seat labels
     * @param count expected number of seats
     * @return "VALID" or error message
     */
    public String validateMultipleSeatSelection(List<String> labels, int count) {

        // Check if number of seats matches requested count
        if (labels.size() != count) {
            return "Seat count mismatch.";
        }

        // Check for duplicate seat entries
        Set<String> uniqueSeats = new HashSet<>(labels);
        if (uniqueSeats.size() != labels.size()) {
            return "Duplicate seats selected. Please select unique seats.";
        }

        // Validate each seat
        for (String label : labels) {

            Seat seat = getSeatByLabel(label);

            // Check if seat exists
            if (seat == null) {
                return "Invalid seat: " + label;
            }

            // Check if already booked
            if (seat.getStatus().equalsIgnoreCase("BOOKED")) {
                return "Seat already booked: " + label;
            }
        }

        return "VALID";
    }
}