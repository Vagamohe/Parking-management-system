package models.vehicls;

import java.io.Serializable;

/* Car class: represents a standard vehicle type */
public class Car extends Vehicle implements Serializable {
	/* Constructor Car initializes plate and brand */
	public Car(int licensPlate,String brand) {
		super(licensPlate,brand);
	}
	@Override
	/* Returns text description of the car */
	public String toString() {
	    return "Car {plate=" + getLicensPlate() + "/" + ", brand=" + getBrand() + "/" +"}";
	}
}
