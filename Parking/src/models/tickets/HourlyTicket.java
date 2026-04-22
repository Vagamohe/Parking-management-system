package models.tickets;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import models.vehicls.Vehicle;

/* HourlyTicket class : represents an hourly ticket for vehicles */
public class HourlyTicket extends Tickets implements Serializable {
	private final int HourRate=50;// Hourly rate for the ticket
	private int hourlyTicketId;// Unique ID for the hourly ticket
	Random ran=new Random();//Random generator for ticket ID
	/* Constructor HourlyTicket : initializes vehicle and generates ticket ID */
	public HourlyTicket(Vehicle vehicle) {
		 super(vehicle);
		 this.hourlyTicketId=ran.nextInt(100)+1;
	}
	/* Calculate the bill based on entry and exit time */
	@Override
	public double calculateBill(){
		if ((ChronoUnit.HOURS.between(entryTime, exitTime)>=24)){
			return 200 * ChronoUnit.DAYS.between(entryTime.toLocalDate(), exitTime.toLocalDate()) ;
		}else {
		return HourRate*(ChronoUnit.HOURS.between(entryTime, exitTime));
	    }
	}
	/* Get the ticket ID */
	public int getTicketId() {
		return this.hourlyTicketId;
	}
	 public int getFee() {
		 return HourRate;
	 }

}
