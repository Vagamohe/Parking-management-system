package models.spots;

import java.io.Serializable;

import models.vehicls.Vehicle;

/* CompactSpot class : spot for compact vehicles (e.g., small cars) */
public class CompactSpot extends Spots implements Serializable{
	 /* Constructor CompactSpot : initializes spot ID */
	public CompactSpot(String Spotid) {
		super (Spotid);
	}
	 /* Occupy the spot */
	@Override
	public  void occupy(Vehicle vehicle) {
		available=false;
	}
	/* Release the spot */
	public void release() {
		available=true;
	}
}
