package exceptions;

/* TicketNotFoundException class : thrown when a ticket is not found */
public class TicketNotFoundException extends Exception {
	public TicketNotFoundException(String message) {
		super (message);
	}

}
