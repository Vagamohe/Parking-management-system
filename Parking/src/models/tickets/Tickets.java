package models.tickets;
import java.io.Serializable;
import java.time.LocalDateTime;
import interfaces.billable;
import models.vehicls.Vehicle;

/* Tickets abstract class : abstract class for all types of tickets */
public abstract class Tickets implements billable,Serializable {
    protected Vehicle vehicle;// Vehicle associated with the ticket
    protected LocalDateTime entryTime;// Entry time of the vehicle
    protected LocalDateTime exitTime;// Exit time of the vehicle
    /* Constructor Tickets : initializes vehicle and entry time */
    public Tickets(Vehicle vehicle) {
		this.vehicle=vehicle;
		this.entryTime=LocalDateTime.now();
	}
    /* Get the vehicle associated with the ticket */
	public Vehicle getVehicle() {
		return vehicle;
	}
	 /* Set the exit time to current time */
	public void setExitTime() { 
		 this.exitTime = LocalDateTime.now();
	 }
	/* Abstract method to get ticket ID */
	abstract public int getTicketId() ;
 public LocalDateTime getExitTime() {
	 return this.exitTime;
 }
 abstract public int getFee() ;
}