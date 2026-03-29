package org.expleo.TicketBookingJavaProject.model;

/*
 * Seat Model Class
 * Represents a seat in a theatre (e.g., A1, B5)
 */
public class Seat {

    // Unique identifier for the seat
    private int seatId;

    // Row of the seat (e.g., A, B, C)
    private String row;

    // Seat number in the row (e.g., 1, 2, 3)
    private int number;

    // Status of seat (AVAILABLE, BOOKED)
    private String status;

    // Constructor to initialize all fields
    public Seat(int seatId, String row, int number, String status) {
        this.seatId = seatId;
        this.row = row;
        this.number = number;
        this.status = status;
    }

    // Getter for seat ID
    public int getSeatId() {
        return seatId;
    }

    // Getter for row
    public String getRow() {
        return row;
    }

    // Getter for seat number
    public int getNumber() {
        return number;
    }

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Setter for status (used during booking)
    public void setStatus(String status) {
        this.status = status;
    }

    // Returns seat label like A1, B3
    public String getSeatLabel() {
        return row + number;
    }

    // toString method for display
    @Override
    public String toString() {
        return getSeatLabel() + " - " + status;
    }
}