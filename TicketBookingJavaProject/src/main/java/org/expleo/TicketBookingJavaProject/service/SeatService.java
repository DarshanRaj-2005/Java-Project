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
        List<Seat> seats = repo.getSeats();
        List<Seat> availableSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (seat.getStatus().equals("AVAILABLE")) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }

    public List<Seat> getBookedSeats() {
        List<Seat> seats = repo.getSeats();
        List<Seat> bookedSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (seat.getStatus().equals("BOOKED")) {
                bookedSeats.add(seat);
            }
        }
        return bookedSeats;
    }
    
    public Seat getSeatByLabel(String seatLabel) {
        List<Seat> seats = repo.getSeats();

        for (Seat seat : seats) {
            if (seat.getSeatLabel().equalsIgnoreCase(seatLabel)) {
                return seat;
            }
        }
        return null;
    }

    public String validateSingleSeatSelection(String seatLabel) {
        if (seatLabel == null || seatLabel.isEmpty()) {
            return "Seat number cannot be empty.";
        }

        if (!seatLabel.matches("^[A-J](10|[1-9])$")) {
            return "Invalid seat format. Please enter a valid seat like A1, B5 or J10.";
        }

        Seat seat = getSeatByLabel(seatLabel);

        if (seat == null) {
            return "Seat does not exist.";
        }

        if (seat.getStatus().equalsIgnoreCase("BOOKED")) {
            return "Selected seat is already booked.";
        }

        return "VALID";
    }

    public String validateMultipleSeatSelection(List<String> seatLabels, int ticketCount) {
        if (seatLabels.size() != ticketCount) {
            return "Number of selected seats must match ticket count.";
        }

        for (int i = 0; i < seatLabels.size(); i++) {
            for (int j = i + 1; j < seatLabels.size(); j++) {
                if (seatLabels.get(i).equalsIgnoreCase(seatLabels.get(j))) {
                    return "Duplicate seat selection is not allowed.";
                }
            }
        }

        for (String seatLabel : seatLabels) {
            String result = validateSingleSeatSelection(seatLabel);
            if (!result.equals("VALID")) {
                return "Invalid seat '" + seatLabel + "' : " + result;
            }
        }

        return "VALID";
    }

    public boolean bookSeat(String seatLabel) {
        Seat seat = getSeatByLabel(seatLabel);

        if (seat != null && seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
            seat.setStatus("BOOKED");
            return true;
        }
        return false;
    }

    public List<Seat> bookMultipleSeats(List<String> seatLabels) {
        List<Seat> selectedSeats = new ArrayList<>();

        for (String seatLabel : seatLabels) {
            Seat seat = getSeatByLabel(seatLabel);
            if (seat == null || !seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
                return null;
            }
        }

        for (String seatLabel : seatLabels) {
            Seat seat = getSeatByLabel(seatLabel);
            seat.setStatus("BOOKED");
            selectedSeats.add(seat);
        }

        return selectedSeats;
    }
}
