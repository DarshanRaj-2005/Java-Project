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

    // Getter methods
    public int getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public String getStatus() { return status; }

    // Setter methods
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setMethod(String method) { this.method = method; }
    public void setStatus(String status) { this.status = status; }
}
