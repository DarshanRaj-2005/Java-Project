package org.expleo.TicketBookingJavaProject.util;

import org.expleo.TicketBookingJavaProject.model.PaymentResponse;

public class PaymentUtil {

    public static PaymentResponse processPayment(double amount) {

        System.out.println("\nRedirecting to Payment Module...");
        System.out.println("Processing payment of Rs." + amount);

        return new PaymentResponse("TXN12345", "SUCCESS");
        // return new PaymentResponse("TXN12345", "FAILED");
    }
}