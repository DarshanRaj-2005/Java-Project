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