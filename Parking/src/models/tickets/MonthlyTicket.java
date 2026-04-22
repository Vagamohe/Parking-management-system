package models.tickets;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import models.vehicls.Vehicle;

/* MonthlyTicket class : represents a monthly pass for vehicles */
public class MonthlyTicket extends Tickets implements Serializable {
	private final int MonthRate=4000;// Monthly rate for the ticket
	private int MonthlyTicketId;// Unique ID for the monthly ticket
	Random ran=new Random();// Random generator for ticket ID
	/* Constructor MonthlyTicket : initializes vehicle and generates ticket ID */
	public MonthlyTicket(Vehicle vehicle) {
		 super(vehicle);
		 this.MonthlyTicketId=ran.nextInt(100)+1;
	}
	/* Calculate the bill based on entry and exit month */
	@Override
	public double calculateBill() {
		return MonthRate*(exitTime.getMonthValue()-entryTime.getMonthValue());
	}
	/* Get the ticket ID */
	public int getTicketId() {
		return this.MonthlyTicketId;
	}
	 public int getFee() {
		 return MonthRate;
	 }
}
