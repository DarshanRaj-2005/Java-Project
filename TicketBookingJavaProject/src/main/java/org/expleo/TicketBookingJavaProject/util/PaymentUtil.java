package org.expleo.TicketBookingJavaProject.util;

import org.expleo.TicketBookingJavaProject.model.PaymentResponse;

/*
 * Utility class for payment processing
 */
public class PaymentUtil {

    // Simulates payment processing and returns mock response
    public static PaymentResponse processPayment(double amount) {
        System.out.println("\nRedirecting to Payment Module...");
        System.out.println("Processing payment of Rs." + amount);
        return new PaymentResponse("TXN12345", "SUCCESS");
    }
}
