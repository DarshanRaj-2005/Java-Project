package org.expleo.TicketBookingJavaProject.model;

/**
 * Model class representing a Seat.
 * Contains information about a seat in a theatre.
 */
public class Seat {

    // Unique seat ID
    private int seatId;
    
    // Row letter (A, B, C, etc.)
    private String row;
    
    // Seat number in the row
    private int number;
    
    // Status (AVAILABLE, BOOKED)
    private String status;
    
    // Price of the seat
    private double price;

    /**
     * Constructor to initialize seat.
     */
    public Seat(int seatId, String row, int number, String status, double price) {
        this.seatId = seatId;
        this.row = row;
        this.number = number;
        this.status = status;
        this.price = price;
    }

    // Getter and Setter methods
    public int getSeatId() { 
    	return seatId; 
    }
    public String getRow() { 
    	return row; 
    }
    public int getNumber() { 
    	return number; 
    }
    public String getStatus() { 
    	return status; 
    }
    public void setStatus(String status) { 
    	this.status = status; 
    }
    public double getPrice() { 
    	return price; 
    }
    public void setPrice(double price) { 
    	this.price = price; 
    }

    /**
     * Gets the seat label (e.g., "A1", "B5").
     */
    public String getSeatLabel() {
        return row + number;
    }

    /**
     * String representation of seat.
     */
    @Override
    public String toString() {
        return getSeatLabel() + " - " + status + " - Rs. " + price;
    }
}
