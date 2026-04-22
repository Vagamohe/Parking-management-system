package models.tickets;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import models.vehicls.Vehicle;
/* DailyTicket class : represents a daily ticket for vehicles */
public class DailyTicket extends Tickets implements Serializable {
	private final int DayRate=200;// Daily rate for the ticket
	private int DailyTicketId;// Unique ID for the daily ticket
	Random ran=new Random();//Random generator for ticket ID
	/* Constructor DailyTicket : initializes vehicle and generates ticket ID */
	public DailyTicket(Vehicle vehicle) {
		 super(vehicle);
		 this.DailyTicketId=ran.nextInt(100)+1;
	}
	/* Calculate the bill based on entry and exit time */
	@Override
	public double calculateBill() {
		if (ChronoUnit.DAYS.between(entryTime.toLocalDate(), exitTime.toLocalDate())>29) {
			return 4000* ChronoUnit.MONTHS.between(entryTime, exitTime);
		}else {
		return DayRate* ChronoUnit.DAYS.between(entryTime.toLocalDate(), exitTime.toLocalDate());
  	    }
	}
	/* Get the ticket ID */
	public int getTicketId() {
		return this.DailyTicketId;
	}
	 public int getFee() {
		 return DayRate;
	 }
}
