package services;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptions.SpotNotAvailableException;
import models.*;
import models.spots.RegularSpot;
import models.spots.Spots;
import models.tickets.Tickets;
import models.vehicls.Vehicle;
import services.SpotManagement;
import models.tickets.*;

/* EntryRegistration class : handles vehicle entry and ticket registration */
public class EntryRegistration implements Serializable{
	private List<Vehicle> entryvehicle;// List of vehicles currently in the parking// List of active tickets
	private SpotManagement spotManagement;// Reference to spot management service
	Tickets t;
	Vehicle r;
	Statement st;
	public EntryRegistration(SpotManagement spotManagement) throws SQLException {
        this.spotManagement = spotManagement;
        this.entryvehicle = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/my_project";
        String user = "root";
        String password = "";
        Connection conn = DriverManager.getConnection(url, user, password);
        st=conn.createStatement();
	}
	public <T extends Spots> Tickets registerEntry(Vehicle vehicle,Class<T> spottype,String tickettype) throws SpotNotAvailableException, SQLException, IOException{
		r=vehicle; 
		if (isVehicleRegistered(vehicle.getLicensPlate())) {
		      System.out.println("This vehicle is already registered.");
		      return null; 
		 }
		 T spot = spotManagement.findAvailableSpotByType(spottype);
		 if (spot==null) {
			 throw new SpotNotAvailableException("sorry there is not available spot");
		 }else {
			 spotManagement.ReserveSpotbytype(vehicle, spot);
			 switch (tickettype.toUpperCase()) {
			 case "HOURLY" : t = new HourlyTicket(vehicle);
			 break;
			 case "DAILY" : t = new DailyTicket(vehicle);
			 break;
			 case "MONTHLY" : t = new MonthlyTicket(vehicle);
			 break;
			 default : t=null;
			 break;
			 }		   
		     savesVehicles.saveData(vehicle);
			 TicketsStorage.saveTickets(t,vehicle);
			 if(spot !=null) {
				    String sql = "UPDATE spots SET Occupied = ? WHERE spotId = ?";
				    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_project", "root", "");
				         PreparedStatement ps = conn.prepareStatement(sql)) {
				        ps.setBoolean(1, true);
				        ps.setString(2, spot.getSpotID());
				        ps.executeUpdate();
				    }
				}
		 return t;
		}
	}
	public boolean isVehicleRegistered(int licensePlate) throws SQLException {
		String sql = "SELECT * FROM vehicls WHERE licence_plate = " + licensePlate;
		ResultSet r=st.executeQuery(sql);
		if (!r.next()) {
		    return false;
		} else {
		    return true;
		}
	}
	/* Get the list of active tickets */
	/* Get the list of vehicles currently entered */
	public Vehicle getEntryvehicle() {
        return r;
}
}
