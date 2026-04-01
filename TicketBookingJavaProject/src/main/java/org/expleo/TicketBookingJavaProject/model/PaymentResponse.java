package org.expleo.TicketBookingJavaProject.model;

/*
 * PaymentResponse Model Class
 * Represents the response received after payment processing
 * Contains transaction details and payment status
 */
public class PaymentResponse {

	// Unique transaction ID generated during payment
	private String transactionId;

	// Status of the payment (SUCCESS or FAILED)
	private String status;

	/*
	 * Constructor to initialize payment response
	 * 
	 * @param transactionId Unique transaction identifier
	 * 
	 * @param status Payment status
	 */
	public PaymentResponse(String transactionId, String status) {
		this.transactionId = transactionId;
		this.status = status;
	}

	/*
	 * Getter for transaction ID
	 * 
	 * @return transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/*
	 * Getter for payment status
	 * 
	 * @return status (SUCCESS / FAILED)
	 */
	public String getStatus() {
		return status;
	}
}