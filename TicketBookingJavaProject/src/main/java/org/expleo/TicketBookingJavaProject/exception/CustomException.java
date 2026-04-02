package org.expleo.TicketBookingJavaProject.exception;

/*
 * Base runtime exception class for custom exceptions
 */
public class CustomException extends RuntimeException {
    
    // Constructor accepts error message
    public CustomException(String message) {
        super(message);
    }
}
