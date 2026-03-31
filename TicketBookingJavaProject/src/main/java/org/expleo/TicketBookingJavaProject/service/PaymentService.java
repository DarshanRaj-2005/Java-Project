package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Payment;
<<<<<<< HEAD

public class PaymentService {

    public Payment processPayment(double amount, String method) {

        Payment payment = new Payment();
=======
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
>>>>>>> 35d0e4124a3a2dc0a7a67de3fb3e0d2628a3f533
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus("SUCCESS");

<<<<<<< HEAD
        System.out.println("Payment of Rs." + amount + " done using " + method);
=======
        // Display payment success message
        System.out.println("Payment Successful using " + method);
>>>>>>> 35d0e4124a3a2dc0a7a67de3fb3e0d2628a3f533

        return payment;
    }

<<<<<<< HEAD
    public boolean validatePayment(Payment payment) {
        return payment.getStatus().equalsIgnoreCase("SUCCESS");
    }
}
=======
    /*
     * Validates if payment is successful
     * @param payment Payment object to validate
     * @return true if payment status is SUCCESS
     */
    public boolean validatePayment(Payment payment) {
        return payment.getStatus().equalsIgnoreCase("SUCCESS");
    }
}
>>>>>>> 35d0e4124a3a2dc0a7a67de3fb3e0d2628a3f533
