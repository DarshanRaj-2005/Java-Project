package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Payment;
import java.util.Random;

/*
 * Service class handles payment processing
 * Manages payment transactions
 */
public class PaymentService {

    /*
     * Processes payment for given amount
     * @param amount Payment amount
     * @param method Payment method (CARD/UPI/CASH)
     * @return Payment object with transaction details
     */
    public Payment processPayment(double amount, String method) {

        // Create new payment object
        Payment payment = new Payment();
        payment.setPaymentId(new Random().nextInt(1000));
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus("SUCCESS");

        // Display payment success message
        System.out.println("Payment Successful using " + method);

        return payment;
    }

    /*
     * Validates if payment is successful
     * @param payment Payment object to validate
     * @return true if payment status is SUCCESS
     */
    public boolean validatePayment(Payment payment) {
        return payment.getStatus().equalsIgnoreCase("SUCCESS");
    }
}
