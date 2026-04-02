package org.expleo.TicketBookingJavaProject.exception;

/*
 * Custom exception for payment-related errors
 */
public class PaymentErrorException extends CustomException {
    
    // Constructor passes message to parent CustomException
    public PaymentErrorException(String message) {
        super(message);
    }
}
