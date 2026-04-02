package org.expleo.TicketBookingJavaProject.exception;

/*
 * Exception thrown when booking ID is not found in database
 */
public class BookingNotFoundException extends CustomException {
    
    // Constructor passes message to parent CustomException
    public BookingNotFoundException(String message) {
        super(message);
    }
}
