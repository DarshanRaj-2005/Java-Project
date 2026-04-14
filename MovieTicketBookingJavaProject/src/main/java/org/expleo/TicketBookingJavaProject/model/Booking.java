package org.expleo.TicketBookingJavaProject.model;

import java.util.List;

public class Booking {

    // Unique booking ID (Example: BK1712667123456)
    private String bookingId;

    // User ID from users table
    private String userId;

    // Movie ID
    private String movieId;

    // Theatre ID
    private int theatreId;

    // Show time (Example: "10:00 AM")
    private String showtime;

    // Session key used internally for seat mapping
    // Example: MOV001_1_10:00 AM
    private String sessionKey;

    // Seat labels used by team members
    // Example: ["A1", "A2"]
    private List<String> seatLabels;

    // Rohini module: complete Seat objects
    private List<Seat> bookedSeats;

    // Total booking amount
    private double totalAmount;

    // Booking status
    // Example: CONFIRMED / CANCELLED
    private String status;

    // Default constructor
    public Booking() {
    }

    // Constructor without bookedSeats
    public Booking(String bookingId, String userId, String movieId,
                   int theatreId, String showtime, String sessionKey,
                   List<String> seatLabels,
                   double totalAmount, String status) {

        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showtime = showtime;
        this.sessionKey = sessionKey;
        this.seatLabels = seatLabels;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Constructor with bookedSeats
    public Booking(String bookingId, String userId, String movieId,
                   int theatreId, String showtime, String sessionKey,
                   List<String> seatLabels, List<Seat> bookedSeats,
                   double totalAmount, String status) {

        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showtime = showtime;
        this.sessionKey = sessionKey;
        this.seatLabels = seatLabels;
        this.bookedSeats = bookedSeats;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public List<String> getSeatLabels() {
        return seatLabels;
    }

    public void setSeatLabels(List<String> seatLabels) {
        this.seatLabels = seatLabels;
    }

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(List<Seat> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}