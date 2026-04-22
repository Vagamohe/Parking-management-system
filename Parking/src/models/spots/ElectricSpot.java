package models.spots;

import java.io.Serializable;

import models.vehicls.Vehicle;
/* ElectricSpot class : spot for electric vehicles */
public class ElectricSpot extends Spots implements Serializable{
	/* Constructor ElectricSpot : initializes spot ID */
	public ElectricSpot(String Spotid) {
		super (Spotid);
	}
	/* Occupy the spot */
	@Override
	public void occupy(Vehicle vehicle) {
		available=false;
	}
	/* Release the spot */
	public void release() {
		available=true;
	}
}
