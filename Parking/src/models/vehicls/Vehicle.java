package models.vehicls;
import java.io.Serializable;

import interfaces.Parkable;
import models.spots.Spots;

/* The abstract class Vehicle represent any type of vechicle */
public abstract class Vehicle implements Parkable, Serializable {
	/* The three attributes (licensPlate, brand, spot) with use Encapsulation : */
	private int licensPlate; // The unique license plate of the vehicle.
	private String brand; // The brand or manufacturer of the vehicle.
	Spots spot; // The parking spot assigned to this vehicle.
	/* Constructor Vehicle with use "this" notation :*/
	public Vehicle(int licensPlate,String brand) {
		this.licensPlate=licensPlate;
		this.brand=brand;
	}
	/* Getters */
	public int getLicensPlate() {
		return licensPlate;
	}
	public  String getBrand() {
		return brand;
	}
	// Gets the parking spot currently used by this vehicle.
    public Spots getSpot() {
        return spot;
    }
    /* Setters :*/
    // Gets the parking spot currently used by this vehicle.
    public void setSpot(Spots spot) {
        this.spot = spot;
    } 
	@Override 
	public void park() {
		System.out.println("vehicel parked: "+licensPlate);
	}
	@Override 
	public void leave() {
		System.out.println("vehicel leave: "+licensPlate);
	}
	@Override
	// Returns a formatted string describing the vehicle.
	public String toString() {
	    return getClass().getSimpleName() + " { plate=" + getLicensPlate() + "/" + ", brand=" + getBrand() + " }";
	}

}
