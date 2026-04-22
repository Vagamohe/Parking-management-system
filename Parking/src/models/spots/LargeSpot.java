package models.spots;

import java.io.Serializable;

import models.vehicls.Vehicle;
/* LargeSpot class : spot for large vehicles (e.g., trucks) */
	public class LargeSpot extends Spots implements Serializable{
		/* Constructor LargeSpot : initializes spot ID */
		public LargeSpot(String Spotid) {
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
