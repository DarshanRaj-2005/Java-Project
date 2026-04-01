package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.service.PaymentService;

/*
 * Controller acts as bridge between UI and Service layer
 * Handles payment-related requests
 */
public class PaymentController {

	// Service instance for payment operations
	private PaymentService service = new PaymentService();

	/*
	 * Initiates payment processing
	 * 
	 * @param amount Payment amount
	 * 
	 * @param method Payment method
	 * 
	 * @return Payment object with transaction details
	 */
	public Payment makePayment(double amount, String method) {
		return service.processPayment(amount, method);
	}

	/*
	 * Validates payment status
	 * 
	 * @param payment Payment to validate
	 * 
	 * @return true if payment is successful
	 */
	public boolean checkPayment(Payment payment) {
		return service.validatePayment(payment);
	}
}
