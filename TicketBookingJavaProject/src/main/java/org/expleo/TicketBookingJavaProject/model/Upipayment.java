package org.expleo.TicketBookingJavaProject.model;

public class Upipayment extends Payment {
    private String upiId;

    public Upipayment(int paymentId, double amount, String status, String upiId) {
        super(paymentId, amount, "UPI", status);
        this.upiId = upiId;
    }

    public String getUpiId() { return upiId; }
}
