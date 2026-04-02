package org.expleo.TicketBookingJavaProject.model;

/*
 * Payment Model Class
 * Represents payment information for ticket bookings
 */
public class Payment {

    // Unique identifier for payment transaction
    private int paymentId;
    
    // Total amount to be paid
    private double amount;
    
    // Payment method used (CARD, UPI, CASH)
    private String method;
    
    // Status of payment (SUCCESS, FAILED)
    private String status;

    // Constructor to initialize payment fields
    public Payment(int paymentId, double amount, String method, String status) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    // Getter and Setter methods
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
