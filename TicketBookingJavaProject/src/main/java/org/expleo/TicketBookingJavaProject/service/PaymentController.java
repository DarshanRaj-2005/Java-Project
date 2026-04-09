/*
 * FILE: PaymentController.java (in service package)
 * PURPOSE: Bridge between UI and payment service.
 * - Author Tamil Kumar
 */
package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.service.PaymentService;

/*
 * Controller acts as bridge between UI and Service layer.
 * Handles payment-related requests.
 */
public class PaymentController {

    // Service instance for payment operations
    private PaymentService service = new PaymentService();

    /*
     * makePayment - Processes payment
     */
    public Payment makePayment(double amount, String method) {
        return service.processPayment(amount, method);
    }

    /*
     * checkPayment - Validates payment status
     */
    public boolean checkPayment(Payment payment) {
        return service.validatePayment(payment);
    }
}
