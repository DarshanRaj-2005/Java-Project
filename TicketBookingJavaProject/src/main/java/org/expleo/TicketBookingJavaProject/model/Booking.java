package org.expleo.TicketBookingJavaProject.model;


public class Booking {

    private int bookingId;
    private double totalAmount;
    private String status;

    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    // IMPORTANT
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

/*
 * Booking Model Class
 * Represents a ticket booking in the movie system
 */
public class Booking {

    // Unique identifier for booking
	private String bookingId;
    
    // Total amount for the booking
    private double totalAmount;
    
    // Status of booking (CONFIRMED, FAILED)
    private String status;

    // Getter methods
    public String getBookingId() { return bookingId; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    // Setter methods
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(String status) { this.status = status; }
}

