package models.spots;
import java.io.Serializable;
import models.vehicls.*;
/* RegularSpot class : standard parking spot */
public class RegularSpot extends Spots implements Serializable{
	private Vehicle currentVehicle; //  Vehicle currently occupying the spot
	/* Constructor RegularSpot : initializes spot ID */
	public RegularSpot(String Spotid) {
		super (Spotid);
	}
	/* Occupy the spot with a vehicle */
	@Override
	public  void occupy(Vehicle vehicle) {
		available=false;
		this.currentVehicle=vehicle;
	}
	/* Release the spot */
	public void release() {
		available=true;
		this.currentVehicle = null;
	}
}
