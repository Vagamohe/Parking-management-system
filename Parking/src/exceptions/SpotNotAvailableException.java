package exceptions;

/* SpotNotAvailableException class : thrown when a specific spot is not available */
public class SpotNotAvailableException extends Exception{
	public SpotNotAvailableException(String message) {
		super (message);
	}

}
