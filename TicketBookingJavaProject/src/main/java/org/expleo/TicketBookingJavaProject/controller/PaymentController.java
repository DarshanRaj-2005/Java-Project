package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.service.PaymentService;

/*
 * Controller acts as bridge between UI and Service layer
 * Handles payment-related requests
 * - Author Tamil Kumar
 */
public class PaymentController {

    // Service instance for payment operations
    private PaymentService service = new PaymentService();

    // Initiates payment processing
    public Payment makePayment(double amount, String method) {
        return service.processPayment(amount, method);
    }

    // Validates payment status
    public boolean checkPayment(Payment payment) {
        return service.validatePayment(payment);
    }
}
