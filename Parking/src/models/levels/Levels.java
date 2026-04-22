package models.levels;

import models.spots.Spots;

/* Levels class : abstract class representing a parking level */
public abstract class Levels extends Spots {
	protected String location;// Location or identifier of the level
	/* Constructor Levels : initializes spot ID and level location */
	public Levels(String Spotid,String location) {
		super(Spotid);
		this.location=location;
	}
}