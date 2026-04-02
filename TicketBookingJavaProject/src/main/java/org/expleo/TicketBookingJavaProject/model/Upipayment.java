package org.expleo.TicketBookingJavaProject.model;

/*
 * Upipayment Model Class
 * Represents UPI payment method extending base Payment class
 */
public class Upipayment extends Payment {
    
    // UPI ID (format: user@bank)
    private String upiId;

    // Constructor sets payment method to "UPI"
    public Upipayment(int paymentId, double amount, String status, String upiId) {
        super(paymentId, amount, "UPI", status);
        this.upiId = upiId;
    }

    // Getter for UPI ID
    public String getUpiId() { return upiId; }
}
