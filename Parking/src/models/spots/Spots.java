package models.spots;

import java.io.Serializable;

import models.vehicls.Vehicle;

/* Spots class: base class for all parking spot types */
public abstract class Spots implements Serializable {
	private String Spotid;// Spot ID (unique identifier for each spot)
	protected boolean available=true,isoccupied=false; // Spot availability state
	//Constructor: creates a new free spot
	public Spots(String Spotid) {
		this.Spotid=Spotid;
		this.available = true;
	}
	 /* Returns the spot ID */
	public String getSpotID() {
		return Spotid;
	}
	/* Returns true if the spot is occupied */
	public boolean isOccupied() {
	    return isoccupied;
	}
	/* Sets the occupied state */
	public void setOccupied(boolean occupied) {
	    this.isoccupied = occupied;
	}

	//the spot is occupied مشغولة
	public abstract void occupy(Vehicle vehicle);
	//the spot is release or free
	public abstract void release();
}