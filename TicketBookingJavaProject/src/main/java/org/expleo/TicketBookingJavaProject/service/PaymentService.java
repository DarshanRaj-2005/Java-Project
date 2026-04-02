package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.exception.PaymentErrorException;
import java.util.Random;

/*
 * Service class handles payment processing
 * Manages payment transactions and validations
 */
public class PaymentService {

    // Processes payment for given amount
    public Payment processPayment(double amount, String method) {
        int paymentId = new Random().nextInt(1000); // Generate random payment ID
        Payment payment = new Payment(paymentId, amount, method, "SUCCESS");
        System.out.println("Payment processed successfully using " + method + "!");
        return payment;
    }

    // Validates credit/debit card details (16 digits, 3-digit CVV)
    public void validateCardPayment(String cardNumber, String cvv) {
        if (cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            throw new PaymentErrorException("Invalid Card Number! Must be exactly 16 digits.");
        }
        if (cvv == null || cvv.length() != 3 || !cvv.matches("\\d+")) {
            throw new PaymentErrorException("Invalid CVV! Must be exactly 3 digits.");
        }
        System.out.println("Card details validated successfully.");
    }

    // Validates UPI ID format (must contain @ and be at least 5 characters)
    public void validateUpiPayment(String upiId) {
        if (upiId == null || !upiId.contains("@") || upiId.length() < 5) {
            throw new PaymentErrorException("Invalid UPI ID! Format should be: user@bank");
        }
        System.out.println("UPI ID validated successfully.");
    }

    // Validates if payment is successful
    public boolean validatePayment(Payment payment) {
        return payment != null && "SUCCESS".equalsIgnoreCase(payment.getStatus());
    }
}
