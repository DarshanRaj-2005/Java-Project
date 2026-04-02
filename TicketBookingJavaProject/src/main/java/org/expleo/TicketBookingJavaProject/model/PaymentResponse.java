package org.expleo.TicketBookingJavaProject.model;

/*
 * PaymentResponse Model Class
 * Represents the response received after payment processing
 */
public class PaymentResponse {

    // Unique transaction ID generated during payment
    private String transactionId;
    
    // Status of the payment (SUCCESS or FAILED)
    private String status;

    // Constructor to initialize payment response
    public PaymentResponse(String transactionId, String status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    // Getter methods
    public String getTransactionId() { return transactionId; }
    public String getStatus() { return status; }
}
