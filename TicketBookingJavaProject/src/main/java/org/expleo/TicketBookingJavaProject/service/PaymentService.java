package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Payment;

public class PaymentService {

    public Payment processPayment(double amount, String method) {

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus("SUCCESS");

        System.out.println("Payment of Rs." + amount + " done using " + method);

        return payment;
    }

    public boolean validatePayment(Payment payment) {
        return payment.getStatus().equalsIgnoreCase("SUCCESS");
    }
}