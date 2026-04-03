package org.expleo.TicketBookingJavaProject.model;

public class Cardpayment extends Payment {
    private String cardNumber;
    private String cvv;

    public Cardpayment(int paymentId, double amount, String status, String cardNumber, String cvv) {
        super(paymentId, amount, "CARD", status);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public String getCardNumber() { return cardNumber; }
    public String getCvv() { return cvv; }
}
