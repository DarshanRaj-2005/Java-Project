package org.expleo.TicketBookingJavaProject.model;

public class Seat {

    // Unique seat ID from database
    private int seatId;

    // Show/session identifier
    private String sessionKey;

    // Seat label shown to user (A1, A2, B5...)
    private String seatLabel;

    // Seat status: AVAILABLE / BOOKED
    private String status;

    // Seat price
    private double price;

    // Default constructor
    public Seat() {
    }

    // Constructor used in DAO
    public Seat(int seatId, String sessionKey, String seatLabel,
                String status, double price) {

        this.seatId = seatId;
        this.sessionKey = sessionKey;
        this.seatLabel = seatLabel;
        this.status = status;
        this.price = price;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSeatLabel() {
        return seatLabel;
    }

    public void setSeatLabel(String seatLabel) {
        this.seatLabel = seatLabel;
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

    // Utility method
    public boolean isBooked() {
        return "BOOKED".equalsIgnoreCase(status);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", sessionKey='" + sessionKey + '\'' +
                ", seatLabel='" + seatLabel + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }
}