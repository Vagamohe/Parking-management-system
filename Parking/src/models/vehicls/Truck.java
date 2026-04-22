package models.vehicls;

/* Truck class: represents a truck vehicle */
public class Truck extends Vehicle {
	/* Constructor Truck: initializes plate and brand */
	public Truck(int licensPlate,String brand) {
		super(licensPlate,brand);
	}
	/* Returns text description of the truck */
	@Override
	public String toString() {
	    return "Truck {plate=" + getLicensPlate() + "/" + ", brand=" + getBrand() + "/" +"}";
	}
}
