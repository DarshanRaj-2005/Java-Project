package org.expleo.TicketBookingJavaProject.model;

/*
 * Cardpayment Model Class
 * Represents credit/debit card payment extending base Payment class
 */
public class Cardpayment extends Payment {
    
    // Card number (16 digits)
    private String cardNumber;
    
    // Card verification value (3 digits)
    private String cvv;

    // Constructor sets payment method to "CARD"
    public Cardpayment(int paymentId, double amount, String status, String cardNumber, String cvv) {
        super(paymentId, amount, "CARD", status);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    // Getter methods for card details
    public String getCardNumber() { return cardNumber; }
    public String getCvv() { return cvv; }
}
