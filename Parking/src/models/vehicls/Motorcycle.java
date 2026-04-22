package models.vehicls;

import java.io.Serializable;

/* Motorcycle class: represents a motorcycle vehicle */
public class Motorcycle extends Vehicle implements Serializable{
	/* Constructor Motorcycle : initializes plate and brand */
	public Motorcycle(int licensPlate,String brand) {
		super(licensPlate,brand);
	}
	/* Returns text description of the motorcycle */
	@Override
	public String toString() {
	    return "Motorcycle {plate=" + getLicensPlate() + "/" + ", brand=" + getBrand() + "/" +"}";
	}
}
