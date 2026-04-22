package exceptions;

/* ParkingFullException class : thrown when the parking is full */
public class ParkingFullException extends Exception {
	public ParkingFullException(String message) {
		super (message);
	}
}
