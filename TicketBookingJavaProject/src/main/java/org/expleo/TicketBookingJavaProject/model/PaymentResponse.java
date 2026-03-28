package org.expleo.TicketBookingJavaProject.model;

public class PaymentResponse {
    private String transactionId;
    private String status; 

    public PaymentResponse(String transactionId, String status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }
}