package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.exception.PaymentErrorException;
import java.util.Random;

/**
 * Service class for payment operations.
 * Handles payment validation and processing.
 */
public class PaymentService {

    /**
     * Processes a payment.
     * @param amount Payment amount
     * @param method Payment method
     * @return Payment object with transaction details
     */
    public Payment processPayment(double amount, String method) {
        int paymentId = new Random().nextInt(1000);
        Payment payment = new Payment(paymentId, amount, method, "SUCCESS");
        System.out.println("Payment processed successfully using " + method + "!");
        return payment;
    }

    /**
     * Validates card payment details.
     * @param cardNumber Card number (must be 16 digits)
     * @param cvv CVV (must be 3 digits)
     * @throws PaymentErrorException if validation fails
     */
    public void validateCardPayment(String cardNumber, String cvv) {
        // Validate card number
        if (cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            throw new PaymentErrorException("Invalid Card Number! Must be exactly 16 digits.");
        }
        
        // Validate CVV
        if (cvv == null || cvv.length() != 3 || !cvv.matches("\\d+")) {
            throw new PaymentErrorException("Invalid CVV! Must be exactly 3 digits.");
        }
        
        System.out.println("Card details validated successfully.");
    }

    /**
     * Validates UPI payment ID.
     * @param upiId UPI ID (must contain @ symbol)
     * @throws PaymentErrorException if validation fails
     */
    public void validateUpiPayment(String upiId) {
        if (upiId == null || !upiId.contains("@") || upiId.length() < 5) {
            throw new PaymentErrorException("Invalid UPI ID! Format should be: user@bank");
        }
        
        System.out.println("UPI ID validated successfully.");
    }

    /**
     * Validates payment status.
     * @param payment Payment to validate
     * @return true if payment is successful
     */
    public boolean validatePayment(Payment payment) {
        return payment != null && "SUCCESS".equalsIgnoreCase(payment.getStatus());
    }
}
