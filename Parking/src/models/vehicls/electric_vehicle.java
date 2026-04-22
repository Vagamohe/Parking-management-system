package models.vehicls;
import java.io.Serializable;
import interfaces.chargeable;

/* ElectricVehicle class: represents an electric vehicle */
public class electric_vehicle extends Vehicle implements chargeable, Serializable {
	 /* Constructor electric_vehicle : initializes plate and brand */
	public electric_vehicle(int licensPlate,String brand) {
		super(licensPlate,brand);
	}
	/* Charge action for electric vehicles */
	@Override
	public void charge() {
		System.out.println("ُElectric vehicle is charging ...");	
	}
	/* Returns text description of the electric vehicle */
	@Override
	public String toString() {
	    return "Electric vehicle {plate=" + getLicensPlate() + "/" + ", brand=" + getBrand() + "/" +"}";
	}
}
