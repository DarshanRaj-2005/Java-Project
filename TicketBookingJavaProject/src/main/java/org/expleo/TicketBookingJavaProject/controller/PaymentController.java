package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.service.PaymentService;

public class PaymentController {

    PaymentService service = new PaymentService();

    public Payment makePayment(double amount, String method) {
        return service.processPayment(amount, method);
    }

    public boolean checkPayment(Payment payment) {
        return service.validatePayment(payment);
    }
}