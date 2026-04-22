package exceptions;

/* InvalidPaymentException class : thrown when a payment is invalid */
public class InvalidPaymentException extends Exception {
	public InvalidPaymentException(String message) {
		super (message);
	}
}
