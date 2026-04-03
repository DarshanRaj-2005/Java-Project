package org.expleo.TicketBookingJavaProject.model;

import java.util.List;

/**
 * Model class representing a Booking.
 * Contains all information about a ticket booking.
 */
public class Booking {

    // Unique booking ID
    private String bookingId;
    
    // Movie ID for the booking
    private String movieId;
    
    // Theatre ID for the booking
    private int theatreId;
    
    // Showtime
    private String showtime;
    
    // List of booked seat labels
    private List<String> seatLabels;
    
    // Total amount to pay
    private double totalAmount;
    
    // Booking status (CONFIRMED, CANCELLED)
    private String status;

    /**
     * Constructor to initialize booking.
     */
    public Booking(String bookingId, String movieId, int theatreId, String showtime, 
                   List<String> seatLabels, double totalAmount, String status) {
        this.bookingId = bookingId;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showtime = showtime;
        this.seatLabels = seatLabels;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getter methods
    public String getBookingId() { return bookingId; }
    public String getMovieId() { return movieId; }
    public int getTheatreId() { return theatreId; }
    public String getShowtime() { return showtime; }
    public List<String> getSeatLabels() { return seatLabels; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    // Setter methods
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public void setTheatreId(int theatreId) { this.theatreId = theatreId; }
    public void setShowtime(String showtime) { this.showtime = showtime; }
    public void setSeatLabels(List<String> seatLabels) { this.seatLabels = seatLabels; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(String status) { this.status = status; }
}
